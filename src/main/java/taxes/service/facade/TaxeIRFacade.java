package taxes.service.facade;


import org.springframework.data.domain.PageRequest;
import taxes.bean.TaxeIR;
import taxes.ws.dto.ResStatDto;

import java.util.List;

public interface TaxeIRFacade {
  public   TaxeIR findByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice);

//  public   List<TaxeIR> findBySocieteAndDateDeclaration(Societe societe, Date dateDeclaration);

  public int deleteByMoisAndAnneeAndSocieteIce(int mois, int annee, String ice);
  public  int save(TaxeIR taxeIR);

  public  int updateTaxeIR(TaxeIR taxeIR);

  public List<ResStatDto> calculStatic(int annee);

  public List<TaxeIR> findAll(PageRequest page);
}
