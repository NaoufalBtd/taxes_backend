package taxes.service.impl;

import jakarta.transaction.Transactional;
import taxes.bean.FactureGagne;
import taxes.bean.TaxeIS;
import taxes.dao.FactureGagneDao;
import taxes.service.facade.FactureGagneFacade;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FactureGagneService implements FactureGagneFacade {
    @Autowired
    FactureGagneDao factureGagneDao;
    @Autowired
    SocieteService societeService;
//    @Autowired
//    TaxeIsService taxeIsService;

    public List<FactureGagne> findBySocieteIceAndDateFactureBetween(String ice, Date startDate, Date endDate) {
        return factureGagneDao.findBySocieteIceAndDateFactureBetween(ice, startDate, endDate);
    }

    @Transactional
    public int save(FactureGagne factureGagne)  {
        if (factureGagne.getSociete() == null || factureGagne.getSociete().getIce() == null || societeService.findByIce(factureGagne.getSociete().getIce()) == null) {
            return -1;
        } else {
            double tva = factureGagne.getMontantHT() * factureGagne.getTva() / 100;
            factureGagne.setMontantTTC(factureGagne.getMontantHT() + tva);
            factureGagneDao.save(factureGagne);

//            TaxeIS taxeIS = taxeIsService.findByAnneeAndTrimestre(factureGagne.getTaxeIS().getAnnee(), factureGagne.getTaxeIS().getTrimestre());
//            taxeIS.setChiffreAffaire(taxeIS.getChiffreAffaire() + factureGagne.getMontantTTC());
//            taxeIS.setCharge(taxeIS.getCharge() + factureGagne.getMontantHT());


            return 1;
        }
    }






}
