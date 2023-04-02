package taxes.service.impl;

import jakarta.transaction.Transactional;
import taxes.bean.TauxTaxeIR;
import taxes.dao.TauxTaxeIRDao;
import taxes.service.facade.TauxTaxeIRFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TauxTaxeIRService implements TauxTaxeIRFacade {

    @Autowired
    TauxTaxeIRDao tauxTaxeIRDao;


    public List<TauxTaxeIR> findAll() {
        return tauxTaxeIRDao.findAll();
    }



    public int deleteByPourcentage(double pourcentage) {
        return tauxTaxeIRDao.deleteByPourcentage(pourcentage);
    }

    @Transactional
    public int save(TauxTaxeIR tauxTaxeIR) {

         if (tauxTaxeIR.getPourcentage() < 0 || tauxTaxeIR.getPourcentage() > 100) {
                return -1 ;}
         else  if (tauxTaxeIR.getSalaireMin() >= tauxTaxeIR.getSalaireMax()){
             return -2;
         }
     else if  (tauxTaxeIR.getDateApplicationDebut() == null
                 || tauxTaxeIR.getDateApplicationFin() == null ||
                 tauxTaxeIR.getDateApplicationDebut().after(tauxTaxeIR.getDateApplicationFin()))
        {
    return -3; }

    else if  (tauxTaxeIR.getSalaireMax() != null && tauxTaxeIR.getSalaireMax() < 0 || tauxTaxeIR.getSalaireMin() < 0){
    return -4 ;
        }

    else {
             tauxTaxeIRDao.save(tauxTaxeIR);
        return 1;

    }}

    public List<TauxTaxeIR> findByPourcentage(double pourcentage) {
        return tauxTaxeIRDao.findByPourcentage(pourcentage);
    }


    public TauxTaxeIR findBySalaireMaxAndSalaireMin(double salaireNet) {
        tauxTaxeIRDao.findBySalaireMaxAndSalaireMin( salaireNet);
        List<TauxTaxeIR> tauxs = tauxTaxeIRDao.findAll();
        TauxTaxeIR taux1 = new TauxTaxeIR();

        for (TauxTaxeIR taux : tauxs) {
            if ((taux.getSalaireMin() <= salaireNet && taux.getSalaireMax() == null) ||
                    (taux.getSalaireMin() <= salaireNet && taux.getSalaireMax() >= salaireNet)) {
                return taux;
            }
        }
        return null;

    }
}


