package taxes.service.impl;

import taxes.bean.TauxTaxeIS;
import taxes.dao.TauxTaxeIsDao;
import taxes.service.facade.TauxTaxeISFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TauxTaxeIsService implements TauxTaxeISFacade {
    @Autowired
    TauxTaxeIsDao tauxTaxeIsDao;

    @Override
    public TauxTaxeIS findByResultatAvantImpot(double resultatAvantImpot) {
        return tauxTaxeIsDao.findByResultatAvantImpot(resultatAvantImpot);
    }

    public TauxTaxeIS findByDateApplicationDebutAndDateApplicationFin(Date dateDebut, Date dateFin) {

        return tauxTaxeIsDao.findByDateApplicationDebutAndDateApplicationFin(dateDebut, dateFin);
    }

    @Transactional
    public int deleteByDateApplicationDebutAndDateApplicationFin(Date dateDebut, Date dateFin) {
        return tauxTaxeIsDao.deleteByDateApplicationDebutAndDateApplicationFin(dateDebut, dateFin);
    }

    //t5rebi9
    public int save(TauxTaxeIS tauxTaxeIS) {
        if (findByDateApplicationDebutAndDateApplicationFin(tauxTaxeIS.getDateApplicationDebut(),tauxTaxeIS.getDateApplicationFin())!= null
                ||tauxTaxeIS.getDateApplicationFin()==tauxTaxeIS.getDateApplicationFin()) {
            return -1;
        } else if (tauxTaxeIS.getPourcentage()==0) {
            return -2;
        } else if (tauxTaxeIS.getResultatMin()==tauxTaxeIS.getResultMax()) {
            return -3;
        } else {
            tauxTaxeIsDao.save(tauxTaxeIS);
            return 1;
        }
    }
    public List<TauxTaxeIS> findAll() {
        return tauxTaxeIsDao.findAll();
    }

    @Override
    public int update(TauxTaxeIS tauxTaxeIS) {
        TauxTaxeIS tauxTaxeIS1 = findByDateApplicationDebutAndDateApplicationFin(tauxTaxeIS.getDateApplicationDebut(),tauxTaxeIS.getDateApplicationFin());
        if (tauxTaxeIS1 == null) {
            return -1;
        }
        tauxTaxeIS1.setPourcentage(tauxTaxeIS.getPourcentage());
        tauxTaxeIS1.setId(tauxTaxeIS.getId());
        tauxTaxeIS1.setDateApplicationFin(tauxTaxeIS.getDateApplicationFin());
        tauxTaxeIS1.setResultMax(tauxTaxeIS.getResultMax());
        tauxTaxeIS1.setResultatMin(tauxTaxeIS.getResultatMin());
        tauxTaxeIsDao.save(tauxTaxeIS1);
        return 1;
    }
}