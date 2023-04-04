package taxes.service.impl;

import taxes.bean.*;
import taxes.dao.TaxeISDao;
import taxes.service.facade.TaxeIsFacade;
import taxes.utils.DateGenerator;
import taxes.ws.dto.ResStatiqueISDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaxeIsService implements TaxeIsFacade {
    @Autowired
    TaxeISDao taxeISDao;
    @Autowired
    SocieteService societeService;
    @Autowired
    TauxTaxeIsService tauxTaxeIsService;
    @Autowired
    FactureGagneService factureGagneService;
    @Autowired
    FacturePerteService facturePerteService;

    @Override
    public TaxeIS findById(Long id) {
        Optional<TaxeIS> taxeIS = taxeISDao.findById(id);
        return taxeIS.orElse(null);
    }

    public TaxeIS findByAnneeAndTrimestreAndSocieteIce(int annee, int  trimestre, String ice) {
        return taxeISDao.findByAnneeAndTrimestreAndSocieteIce(annee, trimestre, ice);
    }
    public TaxeIS findByAnneeAndTrimestre(int annee, int trimestre) {
        return taxeISDao.findByAnneeAndTrimestre(annee, trimestre);
    }
    public int deleteByAnneeAndTrimestre(int annee, int trimestre) {
        return taxeISDao.deleteByAnneeAndTrimestre(annee, trimestre);
    }

    public int deleteByAnneeAndTrimestreAndSocieteIce(int annee, int trimestre, String ice) {
        return taxeISDao.deleteByAnneeAndTrimestreAndSocieteIce(annee, trimestre, ice);
    }

    public int deleteBySocieteIce(String ice) {
        return taxeISDao.deleteBySocieteIce(ice);
    }

    @Override
    public int save(ISItem isItem, Societe societe) {

        if (isItem.getFactureGagnes().isEmpty()) {
            return -1;
        }
        if (isItem.getTaxeIS().getSociete() == null || isItem.getTaxeIS().getSociete().getIce() == null || societeService.findByIce(isItem.getTaxeIS().getSociete().getIce()) == null) {
            return -2;
        } else if (taxeISDao.findByAnneeAndTrimestre(isItem.getTaxeIS().getAnnee(), isItem.getTaxeIS().getTrimestre() ) != null) {
            return -3;
        } else {
            double totaleResultatApresImpot = 0;
            double totaleResultatAvantImpot = 0;
            double totalGain = 0;
            double totalCharge = 0;
            //Get Date range By Annee and Trimestre
            Date[] dateRnage = DateGenerator.generateDateRnage(isItem.getTaxeIS().getAnnee(), isItem.getTaxeIS().getTrimestre());

            //Get Facture Gagne and Facture Perte By Societe and Date Range
            List<FactureGagne> factureGagnes = factureGagneService.findBySocieteIceAndDateFactureBetween(societe.getIce(), dateRnage[0], dateRnage[1]);
            List<FacturePerte> facturePertes = facturePerteService.findBySocieteIceAndDateFactureBetween(societe.getIce(), dateRnage[0], dateRnage[1]);

            //append Facture Gagne and Facture Perte to ISItem
            isItem.getFactureGagnes().addAll(factureGagnes);
            isItem.getFacturePertes().addAll(facturePertes);

            for (FactureGagne factureGagne : isItem.getFactureGagnes()) {
                totalGain+=factureGagne.getMontantHT();
                factureGagne.setTaxeIS(isItem.getTaxeIS());
                factureGagneService.save(factureGagne);
            }
            for (FacturePerte facturePerte : isItem.getFacturePertes()) {
                totalCharge+=facturePerte.getMontantHT();
                facturePerte.setTaxeIS(isItem.getTaxeIS());
                facturePerteService.save(facturePerte);
            }
            isItem.getTaxeIS().setChiffreAffaire(totalGain);
            isItem.getTaxeIS().setCharge(totalCharge);
            // obtenir la date d'échéance
            Date dateEcheance = isItem.getTaxeIS().getDateEcheance();
            // obtenir la date de paiement
            Date datePaiement = isItem.getTaxeIS().getDatePaiement();
            // calculer le nombre de jours de retard
            long nbMoisRetard = ChronoUnit.MONTHS.between(dateEcheance.toInstant(), datePaiement.toInstant());

            if (nbMoisRetard == 1) {
                double montantPenalite = isItem.getTaxeIS().getMontantIs() * 0.1;
                double nouveauMontant = isItem.getTaxeIS().getMontantIs() + montantPenalite;
                isItem.getTaxeIS().setMontantIs(nouveauMontant);
            }
            if (nbMoisRetard > 1) {
                // calculer la pénalité
                double montantPenalite = isItem.getTaxeIS().getMontantIs() * 0.1;
                // calculer la majoration
                double montantMajoration = isItem.getTaxeIS().getMontantIs() * (nbMoisRetard - 1) * 0.05;
                // mettre à jour le montant total
                double nouveauMontant = isItem.getTaxeIS().getMontantIs() + montantPenalite + montantMajoration;
                isItem.getTaxeIS().setMontantIs(nouveauMontant);
            }

            isItem.getTaxeIS().setResultatAvantImpot(isItem.getTaxeIS().getChiffreAffaire() - isItem.getTaxeIS().getCharge());
            TauxTaxeIS tauxTaxeIS = tauxTaxeIsService.tauxTaxeIsDao.findByResultatAvantImpot(isItem.getTaxeIS().getResultatAvantImpot());
            isItem.getTaxeIS().setTauxTaxeIS(tauxTaxeIS);
            isItem.getTaxeIS().setMontantIs(tauxTaxeIS.getPourcentage() * isItem.getTaxeIS().getResultatAvantImpot());
            isItem.getTaxeIS().setResultatApresImpot(isItem.getTaxeIS().getResultatAvantImpot() - isItem.getTaxeIS().getMontantIs());
            taxeISDao.save(isItem.getTaxeIS());

            totaleResultatApresImpot += isItem.getTaxeIS().getResultatApresImpot();
            totaleResultatAvantImpot += isItem.getTaxeIS().getResultatAvantImpot();
            tauxTaxeIsService.save(tauxTaxeIS);

            isItem.getTaxeIS().setResultatAvantImpot(totaleResultatAvantImpot);
            isItem.getTaxeIS().setResultatApresImpot(totaleResultatApresImpot);
            return 1;

        }

    }

@Override
public int save(TaxeIS taxeIS) {
    if (taxeISDao.findByAnneeAndTrimestre(taxeIS.getAnnee(), taxeIS.getTrimestre()) != null) {
        return -1; // taxeIS with same year and trimester already exists
    }
    if (taxeIS.getSociete() == null || taxeIS.getSociete().getIce() == null || societeService.findByIce(taxeIS.getSociete().getIce()) == null) {
        return -2; // invalid societe
    }
    taxeISDao.save(taxeIS);
    return 1; // success
}

    public int updateTaxeIS(TaxeIS taxeIS) {
        if (taxeISDao.findById(taxeIS.getId()).isPresent()) {
            return -1;} // taxeIS not found
        if (taxeISDao.findByAnneeAndTrimestre(taxeIS.getAnnee(), taxeIS.getTrimestre()) != null) {
            return -2; // taxeIS with same year and trimester already exists
        }
        if (taxeIS.getSociete() == null || taxeIS.getSociete().getIce() == null || societeService.findByIce(taxeIS.getSociete().getIce()) == null) {
            return -3; // invalid societe
        }
        taxeISDao.save(taxeIS);
        return 1; // success
    }


    public List<ResStatiqueISDto> calcStatique(int annee ){
        List<ResStatiqueISDto> res=new ArrayList();
        for(int i=1; i<=12; i++){
            res.add(new ResStatiqueISDto(annee,i,taxeISDao.calcStatique(annee,i)));
        } return res;
    }


    public List<TaxeIS> findBySocieteIce(String ice) {
        return taxeISDao.findBySocieteIce(ice);
    }

    public List<TaxeIS> findAll() {
        return taxeISDao.findAll();
    }
}



