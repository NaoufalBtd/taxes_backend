package taxes.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taxes.bean.*;
import taxes.dao.FacturePerteDao;
import taxes.dao.TaxeISDao;
import taxes.service.facade.IsItemFacade;
import taxes.utils.DateGenerator;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor

public class IsItemService implements IsItemFacade {
    private final TaxeISDao taxeISDao;
    private final TaxeIsService taxeIsService;
    private final SocieteService societeService;
    private final FacturePerteService facturePerteService;
    private final FactureGagneService factureGagneService;
    private final TauxTaxeIsService tauxTaxeIsService;

    @Override
    public int save(ISItem isItem, Societe societe) {
        TaxeIS taxeIS = taxeIsService.findById(isItem.getTaxeIS().getId());


        if (isItem.getFactureGagnes().isEmpty()) {
            return -1;
        }
        if (taxeIS.getSociete() == null || societeService.findByIce(taxeIS.getSociete().getIce()) == null) {
            return -2;
        } else if (taxeISDao.findByAnneeAndTrimestre(taxeIS.getAnnee(), taxeIS.getTrimestre() ) != null) {
            return -3;
        } else {
            double totalGain = 0;
            double totalCharge = 0;


            //Get Date range By Annee and Trimestre
            Date[] dateRnage = DateGenerator.generateDateRnage(taxeIS.getAnnee(), taxeIS.getTrimestre());

            //Get Facture Gagne and Facture Perte By Societe and Date Range
            List<FactureGagne> factureGagnes = factureGagneService.findBySocieteIceAndDateFactureBetween(societe.getIce(), dateRnage[0], dateRnage[1]);
            List<FacturePerte> facturePertes = facturePerteService.findBySocieteIceAndDateFactureBetween(societe.getIce(), dateRnage[0], dateRnage[1]);

            //append Facture Gagne and Facture Perte to ISItem
            isItem.getFactureGagnes().addAll(factureGagnes);
            isItem.getFacturePertes().addAll(facturePertes);

            for (FactureGagne factureGagne : isItem.getFactureGagnes()) {
                totalGain+=factureGagne.getMontantHT();
                factureGagne.setTaxeIS(taxeIS);
                factureGagneService.save(factureGagne);
            }
            for (FacturePerte facturePerte : isItem.getFacturePertes()) {
                totalCharge+=facturePerte.getMontantHT();
                facturePerte.setTaxeIS(taxeIS);
                facturePerteService.save(facturePerte);
            }
            taxeIS.setChiffreAffaire(totalGain);
            taxeIS.setCharge(totalCharge);

            taxeIS.setResultatAvantImpot(taxeIS.getChiffreAffaire() - taxeIS.getCharge());
            TauxTaxeIS tauxTaxeIS = tauxTaxeIsService.tauxTaxeIsDao.findByResultatAvantImpot(taxeIS.getResultatAvantImpot());
            taxeIS.setTauxTaxeIS(tauxTaxeIS);
           taxeIS.setMontantIs(tauxTaxeIS.getPourcentage() * taxeIS.getResultatAvantImpot());
            taxeIS.setResultatApresImpot(taxeIS.getResultatAvantImpot() - taxeIS.getMontantIs());

            // obtenir la date d'échéance
            Date dateEcheance = taxeIS.getDateEcheance();
            // obtenir la date de paiement
            Date datePaiement = taxeIS.getDatePaiement();
            // calculer le nombre des mois de retard
            long nbMoisRetard = ChronoUnit.MONTHS.between(dateEcheance.toInstant(), datePaiement.toInstant());

            if (nbMoisRetard == 1) {
                double montantPenalite = taxeIS.getResultatApresImpot() * 0.1;
                double nouveauMontant = taxeIS.getResultatApresImpot() + montantPenalite;
                taxeIS.setMontantIs(nouveauMontant);
            }
            if (nbMoisRetard > 1) {
                // calculer la pénalité
                double montantPenalite = taxeIS.getResultatApresImpot() * 0.1;
                // calculer la majoration
                double montantMajoration = taxeIS.getResultatApresImpot() * (nbMoisRetard - 1) * 0.05;
                // mettre à jour le montant total
                double nouveauMontant = taxeIS.getResultatApresImpot() + montantPenalite + montantMajoration;
                taxeIS.setMontantIs(nouveauMontant);
            }

            taxeIsService.save(taxeIS);

            tauxTaxeIsService.save(tauxTaxeIS);

            return 1;

        }

    }

}
