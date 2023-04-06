package taxes.service.impl;

import taxes.bean.*;
import taxes.dao.TaxeISDao;
import taxes.service.facade.TaxeIsFacade;
import taxes.ws.dto.ResStatiqueISDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
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



