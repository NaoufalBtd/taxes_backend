package taxes.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import taxes.bean.*;
import taxes.dao.TaxeIRDao;
import taxes.dao.TaxeIREmployesDao;
import taxes.service.facade.TaxeIRFacade;
import taxes.ws.dto.ResStatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaxeIRService implements TaxeIRFacade {

    @Autowired
    private TaxeIRDao taxeIRDao;

    @Autowired

    private TaxeIREmployesService taxeIREmployesService;
    @Autowired
    private SocieteService societeService;

    @Autowired
    private TauxTaxeIRService tauxTaxeIRService;

    @Autowired
    private EmployeService employeService;
    @Autowired
    private TaxeIREmployesDao taxeIREmployesDao;

//    public List<TaxeIR> findBySocieteAndDateDeclaration(Societe societe, Date dateDeclaration) {
//        return taxeIRDao.findBySocieteAndDateDeclaration(societe, dateDeclaration);
//    }


    public int deleteByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice) {


        int res2 = taxeIREmployesService.deleteByTaxeIRMoisAndTaxeIRAnneeAndSocieteIce(mois, annee, ice);
        int res1 = taxeIRDao.deleteByMoisAndAnneeAndSocieteIce(mois, annee, ice);
        return res1 + res2;
    }

    public TaxeIR findByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice) {
        return taxeIRDao.findByMoisAndAnneeAndSocieteIce(mois, annee, ice);
    }

    public List<TaxeIR> findAll(PageRequest page) {
        return taxeIRDao.findAll(page).getContent();
    }

    @Transactional
    public int save(TaxeIR taxeIR) {
//        for (TaxeIREmployes taxeIREmployes : taxeIR.getTaxeIREmployes()) {
//            Employe founededemployeIR = employeService.findByCin(taxeIREmployes.getEmploye().getCin());
//            if (founededemployeIR != null) {
//                return -1;
//            }
//        }
        if (taxeIR.getSociete() == null || taxeIR.getSociete().getIce() == null || societeService.findByIce(taxeIR.getSociete().getIce()) == null) {
            return -2;
        } else if (taxeIRDao.findByMoisAndAnneeAndSocieteIce(taxeIR.getMois(), taxeIR.getAnnee(), taxeIR.getSociete().getIce()) != null) {
            return -3;
        }



        // Vérifier si la déclaration est unique pour une même société et une même année fiscale
        TaxeIR existingTaxeIR = taxeIRDao.findByMoisAndAnneeAndSocieteIce(taxeIR.getMois(), taxeIR.getAnnee(), taxeIR.getSociete().getIce());

        if (existingTaxeIR != null ) {
            return -5;
        }

        // Vérifier si les montants de salaires nets et bruts sont positifs
        else if
        (taxeIR.getSalaireNet() < 0 || taxeIR.getSalaireBrute() < 0) {
            return -6;
        } else {
            Societe societe = societeService.findByIce(taxeIR.getSociete().getIce());
            double totalNet = 0;
            double totalBrut = 0;
            taxeIR.setSociete(societe);
            taxeIRDao.save(taxeIR);
            List<Employe> employes = employeService.findBySocieteIce(societe.getIce());
            for (Employe emp : employes) {
                TauxTaxeIR tauxTaxeIR = tauxTaxeIRService.findBySalaireMaxAndSalaireMin(emp.getSalaire());
                if (tauxTaxeIR == null) {
                    return -6;
                }
                TaxeIREmployes taxeIREmployes = new TaxeIREmployes();
                taxeIREmployes.setTaxeIR(taxeIR);
                taxeIREmployes.setEmploye(emp);
                taxeIREmployes.setSociete(societe);
                taxeIREmployes.setTauxTaxeIR(tauxTaxeIR);

                int salaireNet = (int) (emp.getSalaire() - tauxTaxeIR.getPourcentage()/100 * emp.getSalaire());
                int salaireBrute = (int) (emp.getSalaire());

                taxeIREmployes.setSalaireBrute( salaireBrute);
                taxeIREmployes.setSalaireNet(salaireNet);

                totalNet += salaireNet;
                totalBrut += salaireBrute;
                taxeIREmployesDao.save(taxeIREmployes);
            }

            taxeIR.setSalaireBrute(totalBrut);
            taxeIR.setSalaireNet(totalNet);
            taxeIR.setMontantIR(totalBrut);
            taxeIRDao.save(taxeIR);
            return 1;
        }
    }

    public int updateTaxeIR(TaxeIR taxeIR) {
        TaxeIR existingTaxeIR = taxeIRDao.findById(taxeIR.getId()).orElse(null);
        if (existingTaxeIR == null) {
            // gestion d'erreur si l'instance n'existe pas
            return -1;
        }
        existingTaxeIR.setMois(taxeIR.getMois());
        existingTaxeIR.setAnnee(taxeIR.getAnnee());
//        existingTaxeIR.setTauxTaxeIR(taxeIR.getTauxTaxeIR());
        existingTaxeIR.setSociete(taxeIR.getSociete());
        existingTaxeIR.setDateDeclaration(taxeIR.getDateDeclaration());
        existingTaxeIR.setSalaireNet(taxeIR.getSalaireNet());
        existingTaxeIR.setSalaireBrute(taxeIR.getSalaireBrute());
        existingTaxeIR.setMontantIR(taxeIR.getMontantIR());
        taxeIRDao.save(existingTaxeIR);
        return 1;
    }

    public List<ResStatDto> calculStatic(int annee) {

        List<ResStatDto> res = new ArrayList();
        for (int i = 1; i <= 12; i++) {
            res.add(new ResStatDto(annee, i,taxeIRDao.calculStatic(annee,i)));
        }

        return res;
    }


}
