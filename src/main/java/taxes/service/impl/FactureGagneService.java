package taxes.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import taxes.bean.FactureGagne;
import taxes.dao.FactureGagneDao;
import taxes.service.facade.FactureGagneFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FactureGagneService implements FactureGagneFacade {
    @Autowired
    FactureGagneDao factureGagneDao;
    @Autowired
    SocieteService societeService;
//    @Autowired
//    TaxeIsService taxeIsService;
    @Override
public List<FactureGagne> findBySocieteIceAndDateFactureBetween(String ice, Date startDate, Date endDate) {
    return factureGagneDao.findBySocieteIceAndDateFactureBetween(ice, startDate, endDate);
}

    public List<FactureGagne> findBySocieteIceAndDateFactureBetween(String ice, Date startDate, Date endDate, PageRequest pageRequest) {
        return factureGagneDao.findBySocieteIceAndDateFactureBetween(ice, startDate, endDate, pageRequest);
    }

    @Transactional
    public int save(FactureGagne factureGagne)  {
        if (factureGagne.getSociete() == null || factureGagne.getSociete().getIce() == null || societeService.findByIce(factureGagne.getSociete().getIce()) == null) {
            return -1;
        } else {
            double tva = factureGagne.getMontantHT() * 20 / 100;
            factureGagne.setMontantTTC(factureGagne.getMontantHT() + tva);
            factureGagneDao.save(factureGagne);

//            TaxeIS taxeIS = taxeIsService.findByAnneeAndTrimestre(factureGagne.getTaxeIS().getAnnee(), factureGagne.getTaxeIS().getTrimestre());
//            taxeIS.setChiffreAffaire(taxeIS.getChiffreAffaire() + factureGagne.getMontantTTC());
//            taxeIS.setCharge(taxeIS.getCharge() + factureGagne.getMontantHT());


            return 1;
        }
    }

    @Override
    public List<FactureGagne> findBySociete(String ice, PageRequest pageRequest) {
        return factureGagneDao.findBySocieteIce(ice, pageRequest);
    }

    @Override
    public List<FactureGagne> findBySocieteAndDate(String ice, Date startDate, Date endDate, PageRequest pageRequest) {
        return factureGagneDao.findBySocieteIceAndDateFactureBetween(ice, startDate, endDate, pageRequest);
    }
//    @Override
//    public List<Object[]> getLastSixMonthsIncomeInvoicesSumPerMonth(String ice) {
//        Date sixMonthsAgo = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30 * 6));
//        return factureGagneDao.getSumTTCByMonthForLastSixMonths(sixMonthsAgo, ice);
//    }
@Override
    public List<Object[]> getIncomeInvoicesSumByMonth(String ice, long month) {
        Date sixMonthsAgo = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30 * month));
        return factureGagneDao.getSumTTCByMonth(sixMonthsAgo, ice);
    }

    @Override
    public int update(FactureGagne factureGagne) {
        if (factureGagne.getSociete() == null || factureGagne.getSociete().getIce() == null || societeService.findByIce(factureGagne.getSociete().getIce()) == null) {
            return -1;
        } else {
            factureGagneDao.save(factureGagne);
            return 1;
        }
    }

    @Override
    public int delete(Long id) {
        factureGagneDao.deleteById(id);
        return 1;
    }


}
