package taxes.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import taxes.bean.*;
import taxes.dao.ISItemDao;
import taxes.dao.TaxeISDao;
import taxes.service.facade.IsItemFacade;
import taxes.service.utils.DateGenerator;

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
    private final ISItemDao isItemDao;

    @Transactional
    @Override
    public int save(ISItem isItem, Societe societe) {

        TaxeIS taxeIS = taxeIsService.findById(isItem.getTaxeIS().getId());

        //todo: check that the item is not already saved
        if (isItem.getFactureGagnes().isEmpty()) {
            return -1;
        }
        if (taxeIS.getSociete() == null || societeService.findByIce(taxeIS.getSociete().getIce()) == null) {
            return -2;
        } else {
            double totalGain = 0;
            double totalCharge = 0;
            double montantIS = 0;


            //Get Date range By Annee and Trimestre
            Date[] dateRnage = DateGenerator.generateDateRnage(taxeIS.getAnnee(), taxeIS.getTrimestre());

            //Get Facture Gagne and Facture Perte By Societe and Date Range
            List<FactureGagne> factureGagnes = factureGagneService.findBySocieteIceAndDateFactureBetween(societe.getIce(), dateRnage[0], dateRnage[1]);
            List<FacturePerte> facturePertes = facturePerteService.findBySocieteIceAndDateFactureBetween(societe.getIce(), dateRnage[0], dateRnage[1]);

            //append Facture Gagne and Facture Perte to ISItem
            if(isItem.getFactureGagnes() == null) {
                isItem.setFactureGagnes(factureGagnes);
            } else  {
                isItem.getFactureGagnes().addAll(factureGagnes);
            }

            if(isItem.getFacturePertes() == null) {
                isItem.setFacturePertes(facturePertes);
            } else {
                isItem.getFacturePertes().addAll(facturePertes);
            }

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

            double resultatAvantImpot = taxeIS.getChiffreAffaire() - taxeIS.getCharge();
            taxeIS.setResultatAvantImpot(resultatAvantImpot);
            TauxTaxeIS tauxTaxeIS = tauxTaxeIsService.findByResultatAvantImpot(taxeIS.getResultatAvantImpot());
            taxeIS.setTauxTaxeIS(tauxTaxeIS);
            double resultatApresImpot = resultatAvantImpot - (resultatAvantImpot * tauxTaxeIS.getPourcentage()/100);
            taxeIS.setResultatApresImpot(resultatApresImpot);
            taxeIS.setMontantIs(resultatApresImpot);
            // obtenir la date d'échéance
            Date dateEcheance = taxeIS.getDateEcheance();
            // obtenir la date de paiement
            Date datePaiement = new Date();
            // calculer le nombre des mois de retard
            long nbJoursRetard = ChronoUnit.DAYS.between(dateEcheance.toInstant(), datePaiement.toInstant());

            if (nbJoursRetard <= 30) {
                double montantPenalite = taxeIS.getResultatApresImpot() * 0.1;
                double nouveauMontant = taxeIS.getResultatApresImpot() + montantPenalite;
                taxeIS.setMontantIs(nouveauMontant);
            } else {
                // calculer la pénalité
                double montantPenalite = taxeIS.getResultatApresImpot() * 0.1;
                // calculer la majoration
                //todo: fix the calcule logic
                double montantMajoration = taxeIS.getResultatApresImpot() * 2 * 0.05;
                // mettre à jour le montant total
                double nouveauMontant = taxeIS.getResultatApresImpot() + montantPenalite + montantMajoration;
                taxeIS.setMontantIs(nouveauMontant);
            }
//
            taxeIS.setDatePaiement(new Date());
            taxeIsService.save(taxeIS);
            isItemDao.save(isItem);

            return 1;

        }

    }

}
