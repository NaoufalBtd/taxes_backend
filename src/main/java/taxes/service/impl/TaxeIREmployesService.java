package taxes.service.impl;

import jakarta.transaction.Transactional;
import taxes.bean.Employe;
import taxes.bean.TauxTaxeIR;
import taxes.bean.TaxeIR;
import taxes.bean.TaxeIREmployes;
import taxes.dao.TaxeIREmployesDao;
import taxes.service.facade.TaxeIREmployesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxeIREmployesService implements TaxeIREmployesFacade {
    @Autowired
    TaxeIREmployesDao taxeIREmployesDao ;

    public List<TaxeIREmployes> findAll() {
        return taxeIREmployesDao.findAll();
    }

    public TaxeIREmployes findByTaxeIRId(Long id) {
        return taxeIREmployesDao.findByTaxeIRId(id);
    }

@Transactional
    public int save(TaxeIR taxeIR) {
        if (findByTaxeIRId(taxeIR.getId()) != null) {
            return -1;
        } else {
            List<Employe> employes = employeService.findBySocieteIce(taxeIR.getSociete().getIce());
            for(Employe emp : employes) {
                TaxeIREmployes taxeIREmployes = new TaxeIREmployes();
                taxeIREmployes.setTaxeIR(taxeIR);
                taxeIREmployes.setEmploye(emp);
                TauxTaxeIR tauxTaxeIR = tauxTaxeIRService.findBySalaireMaxAndSalaireMin(emp.getSalaire());
                taxeIREmployes.setSalaireBrute( emp.getSalaire() - tauxTaxeIR.getPourcentage() * emp.getSalaire());
                taxeIREmployes.setTauxTaxeIR(tauxTaxeIR);
                taxeIREmployes.setSalaireNet(emp.getSalaire());
                taxeIREmployesDao.save(taxeIREmployes);
            }
            return 1;
        }
    }
    public int deleteByTaxeIRMoisAndTaxeIRAnneeAndSocieteIce(int mois, int annee, String ice) {
        return taxeIREmployesDao.deleteByTaxeIRMoisAndTaxeIRAnneeAndSocieteIce(mois, annee, ice);
    }

    @Autowired
    private TauxTaxeIRService tauxTaxeIRService;
    @Autowired
    private EmployeService employeService;
}
