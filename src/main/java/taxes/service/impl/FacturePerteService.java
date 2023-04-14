package taxes.service.impl;

import org.springframework.data.domain.PageRequest;
import taxes.bean.FacturePerte;
import taxes.bean.TaxeIS;
import taxes.dao.FacturePerteDao;
import taxes.dao.TaxeISDao;
import taxes.service.facade.FacturePerteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FacturePerteService implements FacturePerteFacade {
    @Autowired
    private FacturePerteDao facturePerteDao;
    @Autowired
    private TaxeISDao taxeISDao;

    public List<FacturePerte> findBySocieteIceAndDateFactureBetween(String ice, Date startDate, Date endDate) {
        return  facturePerteDao.findBySocieteIceAndDateFactureBetween(ice, startDate, endDate);
    }

    @Override
    public List<FacturePerte> findBySociete(String ice, PageRequest pageRequest) {
        return facturePerteDao.findBySocieteIce(ice, pageRequest);
    }

    @Override
    public List<FacturePerte> findBySocieteAndDate(String ice, Date startDate, Date endDate, PageRequest pageRequest) {
        return facturePerteDao.findBySocieteIceAndDateFactureBetween(ice, startDate, endDate, pageRequest);
    }

    @Override
    public int delete(Long id) {
         facturePerteDao.deleteById(id);
         return 1;
    }

    public int save(FacturePerte facturePerte) {
        if (facturePerte.getSociete() == null || facturePerte.getSociete().getIce() == null ) {
            return -1;
        } else {
            double tva = facturePerte.getMontantHT() * 20 / 100;
            facturePerte.setMontantTTC(facturePerte.getMontantHT() + tva);
            facturePerteDao.save(facturePerte);
            
            return 1;
        }
    }
    @Override
    public int update(FacturePerte facturePerte) {
        if(facturePerte == null || facturePerte.getId() == null){
            return -1;
        }else{
            facturePerteDao.save(facturePerte);
            return 1;
        }
    }

    @Override
    public List<Object[]> getIncomeInvoicesSumByMonth(String ice, long month) {
        Date sixMonthsAgo = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30 * month));
        return facturePerteDao.getSumTTCByMonth(sixMonthsAgo, ice);
    }




}
