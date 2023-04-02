package taxes.ws.facade;

import taxes.bean.TaxeIREmployes;
import taxes.service.facade.TaxeIREmployesFacade;
import taxes.ws.converter.TaxeIREmployesConverter;
import taxes.ws.dto.TaxeIREmployesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/v1/api/TaxeIREmployes")
public class TaxeIREmployesRest {
    @Autowired
    private TaxeIREmployesFacade taxeIREmployesFacade ;
    @Autowired
    private TaxeIREmployesConverter taxeIREmployesConverter;

    @GetMapping("/")
    public List<TaxeIREmployesDto> findAll() {

        List<TaxeIREmployes> all = taxeIREmployesFacade.findAll();
        return taxeIREmployesConverter.toDto(all);
    }

    @GetMapping("/id/{id}")
    public TaxeIREmployesDto findByTaxeIRId(@PathVariable Long id) {

        TaxeIREmployes taxeIREmployes = taxeIREmployesFacade.findByTaxeIRId(id);
        return taxeIREmployesConverter.toDto(taxeIREmployes);
    }
    @DeleteMapping("/mois/{mois}/annee/{annee}")
    public int deleteByTaxeIRMoisAndTaxeIRAnneeAndSocieteIce(@PathVariable int mois, @PathVariable int annee,@PathVariable String ice ) {
        return taxeIREmployesFacade.deleteByTaxeIRMoisAndTaxeIRAnneeAndSocieteIce(mois, annee,ice);
    }
}
