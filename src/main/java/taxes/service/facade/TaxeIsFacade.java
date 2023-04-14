package taxes.service.facade;

import org.springframework.data.domain.PageRequest;
import taxes.bean.ISItem;
import taxes.bean.Societe;
import taxes.bean.TaxeIS;
import taxes.ws.dto.ResStatiqueISDto;

import java.math.BigDecimal;
import java.util.List;

public interface TaxeIsFacade {

    TaxeIS findById(Long id);

    public TaxeIS findByAnneeAndTrimestreAndSocieteIce(int annee, int trimestre, String ice);
    public TaxeIS findByAnneeAndTrimestre(int annee, int trimestre);

    public int deleteByAnneeAndTrimestre(int annee, int trimestre);

    public int deleteBySocieteIce(String ice);

   public List<TaxeIS> findBySocieteIce(String ice, PageRequest pageRequest);
    public List<TaxeIS> findAll();

     public int deleteByAnneeAndTrimestreAndSocieteIce(int annee, int trimestre, String ice);


    int save(TaxeIS taxeIS, Societe societe);

    int saveTaxesISByTrimester(int annee, int trimester);


    List<TaxeIS>  findUndeclaredTaxes(String ice);

    int declareTax(TaxeIS taxeIS, Societe societe);

    int save(TaxeIS taxeIS);

    public int updateTaxeIS(TaxeIS taxeIS);
    public List<ResStatiqueISDto> calcStatique(int annee);

}
