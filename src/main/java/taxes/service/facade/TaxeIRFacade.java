package taxes.service.facade;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import taxes.bean.Employe;
import taxes.bean.TaxeIR;
import taxes.ws.dto.ResStatDto;

import java.util.List;
import java.util.Optional;

public interface TaxeIRFacade {
  public   TaxeIR findByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice);

//  public   List<TaxeIR> findBySocieteAndDateDeclaration(Societe societe, Date dateDeclaration);

  public int deleteByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice);

  int declareTaxeIR(TaxeIR taxeIR, List<Employe> employes);

  int declareTaxeIR(TaxeIR taxeIR);

  @Transactional
  int save(TaxeIR taxeIR, List<Employe> employes);

  public  int updateTaxeIR(TaxeIR taxeIR);

  public List<ResStatDto> calculStatic(int annee);

  public List<TaxeIR> findAll(PageRequest page);
}
