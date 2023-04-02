package taxes.service.facade;


import taxes.bean.TaxeIREmployes;

import java.util.List;

public interface TaxeIREmployesFacade {
   public TaxeIREmployes findByTaxeIRId(Long id);

   int deleteByTaxeIRMoisAndTaxeIRAnneeAndSocieteIce(int mois, int annee, String ice);
   public List<TaxeIREmployes> findAll();

}
