package taxes.service.impl;

import taxes.bean.FacturePerte;
import taxes.bean.TaxeIS;
import taxes.dao.FacturePerteDao;
import taxes.dao.TaxeISDao;
import taxes.service.facade.FacturePerteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FacturePerteService implements FacturePerteFacade {
    @Autowired
    private FacturePerteDao facturePerteDao;
    @Autowired
    private TaxeISDao taxeISDao;

    public List<FacturePerte> findBySocieteIceAndDateFactureBetween(String ice, Date startDate, Date endDate) {
        return  facturePerteDao.findBySocieteIceAndDateFactureBetween(ice, startDate, endDate);
    }

    public int save(FacturePerte facturePerte) {
        TaxeIS taxeIS = new TaxeIS();
        if (taxeIS == null || taxeIS.getSociete() == null || facturePerte == null || facturePerte.getDateFacture() == null) {
            return -1;
        } else if (facturePerte.getMontantTTC() <= 0) {
            return -2;
        } else if (facturePerte.getMontantTTC() > taxeIS.getChiffreAffaire()) {
            return -3;
        } else {
            facturePerte.setTaxeIS(taxeIS);
            facturePerteDao.save(facturePerte);
            double nouveauCharge = taxeIS.getCharge() + facturePerte.getMontantTTC();
            taxeIS.setCharge(nouveauCharge);
            taxeIS.setResultatAvantImpot(taxeIS.getChiffreAffaire() - taxeIS.getCharge());
            taxeIS.setMontantIs(taxeIS.getTauxTaxeIS().getPourcentage() * taxeIS.getResultatAvantImpot());
            taxeIS.setResultatApresImpot(taxeIS.getResultatAvantImpot() - taxeIS.getMontantIs());
            taxeISDao.save(taxeIS);
            return 1;
        }
    }





}
