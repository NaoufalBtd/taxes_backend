package taxes.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import taxes.bean.*;
import taxes.dao.TaxeISDao;
import taxes.service.facade.TaxeIsFacade;
import taxes.service.utils.DateGenerator;
import taxes.ws.dto.ResStatiqueISDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
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
    public int save(TaxeIS taxeIS, Societe societe) {

        if (taxeISDao.findByAnneeAndTrimestreAndSocieteIce(taxeIS.getAnnee(), taxeIS.getTrimestre(), societe.getIce()) != null) {
            return -1; // taxeIS with same year and trimester already exists
        }
        if (societeService.findByIce(societe.getIce()) == null) {
            return -2; // invalid societe
        }
        taxeIS.setSociete(societe);
        taxeISDao.save(taxeIS);
        return 1; // success

    }

    @Override
    public int saveTaxesISByTrimester(int annee, int trimester) {
        List<Societe> societes = societeService.findAll();
        LocalDate trimDate = LocalDate.of(annee, trimester * 3, 1).atStartOfDay().toLocalDate();
        if(trimDate.isAfter(LocalDate.now())) {
            return -1; // invalid trimester
        }
        for (Societe societe : societes) {
            if(this.findByAnneeAndTrimestreAndSocieteIce(annee, trimester, societe.getIce()) != null) {
                continue;
            }
            TaxeIS taxeIS = new TaxeIS();
            taxeIS.setAnnee(annee);
            taxeIS.setTrimestre(trimester);
            taxeIS.setSociete(societe);
            taxeIS.setDateEcheance(Timestamp.valueOf(trimDate.plusMonths(1).atStartOfDay()));
            taxeISDao.save(taxeIS);
        }
        return 1;

    }

    @Override
    public List<TaxeIS>  findUndeclaredTaxes(String ice) {
        List<TaxeIS> taxeISList = taxeISDao.findBySocieteIce(ice);
        List<TaxeIS> undeclaredTaxes = new ArrayList<>();
        for (TaxeIS taxeIS : taxeISList) {
            if(!taxeIS.getDeclared() ) {
                undeclaredTaxes.add(taxeIS);
            }
        }
        return undeclaredTaxes;
    }

    @Transactional
    @Override
    public int declareTax(TaxeIS taxeIS, Societe societe) {
        TaxeIS tax = taxeISDao.findByAnneeAndTrimestreAndSocieteIce(taxeIS.getAnnee(), taxeIS.getTrimestre(), societe.getIce());
        if (tax.getDeclared()) {
            return -1; // taxe already declared
        }

        if (societe.getIce() == null || societeService.findByIce(taxeIS.getSociete().getIce()) == null) {
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
            if (taxeIS.getFactureGagnes() == null) {
                tax.setFactureGagnes(factureGagnes);
            } else {
                tax.getFactureGagnes().addAll(factureGagnes);
            }

            if (taxeIS.getFacturePertes() == null) {
                tax.setFacturePertes(facturePertes);
            } else {
                tax.getFacturePertes().addAll(facturePertes);
            }

            for (FactureGagne factureGagne : tax.getFactureGagnes()) {
                totalGain += factureGagne.getMontantHT();
                factureGagne.setTaxeIS(tax);
                factureGagneService.save(factureGagne);
            }
            for (FacturePerte facturePerte : tax.getFacturePertes()) {
                totalCharge += facturePerte.getMontantHT();
                facturePerte.setTaxeIS(tax);
                facturePerteService.save(facturePerte);
            }
            tax.setChiffreAffaire(totalGain);
            tax.setCharge(totalCharge);


            double resultatAvantImpot = tax.getChiffreAffaire() - tax.getCharge();
            tax.setResultatAvantImpot(resultatAvantImpot);
            TauxTaxeIS tauxTaxeIS = tauxTaxeIsService.findByResultatAvantImpot(tax.getResultatAvantImpot());

            tax.setTauxTaxeIS(tauxTaxeIS);
            double resultatApresImpot = resultatAvantImpot - (resultatAvantImpot * 10 / 100);
            tax.setResultatApresImpot(resultatApresImpot);
            tax.setMontantIs(resultatApresImpot);
            // obtenir la date d'échéance
            Date dateEcheance = tax.getDateEcheance();
            // obtenir la date de paiement
            Date datePaiement = new Date();
            // calculer le nombre des mois de retard
            long nbJoursRetard = ChronoUnit.DAYS.between(dateEcheance.toInstant(), datePaiement.toInstant());

            if (nbJoursRetard <= 30) {
                double montantPenalite = tax.getResultatApresImpot() * 0.1;
                double nouveauMontant = tax.getResultatApresImpot() + montantPenalite;
                tax.setMontantIs(nouveauMontant);
            } else {
                // calculer la pénalité
                double montantPenalite = tax.getResultatApresImpot() * 0.1;
                // calculer la majoration
                //todo: fix the calcule logic
                double montantMajoration = tax.getResultatApresImpot() * 2 * 0.05;
                // mettre à jour le montant total
                double nouveauMontant = tax.getResultatApresImpot() + montantPenalite + montantMajoration;
                tax.setMontantIs(nouveauMontant);
            }
//
            tax.setDatePaiement(new Date());
            tax.setDeclared(true);
            taxeISDao.save(tax);

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


    public List<TaxeIS> findBySocieteIce(String ice, PageRequest pageRequest) {
        return taxeISDao.findBySocieteIce(ice, pageRequest).getContent();
    }

    public List<TaxeIS> findAll() {
        return taxeISDao.findAll();
    }
}



