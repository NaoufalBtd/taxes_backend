package taxes.ws.facade;

import taxes.bean.TauxTaxeIR;
import taxes.service.facade.TauxTaxeIRFacade;
import taxes.ws.converter.TauxTaxeIRConverter;
import taxes.ws.dto.TauxTaxeIRDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/TauxTaxeIR")
public class TauxTaxeIRRest {

    @Autowired
    private TauxTaxeIRFacade tauxTaxeIRFacade;
    @Autowired
    private TauxTaxeIRConverter tauxTaxeIRConverter;

    @GetMapping  ("/")
    public List<TauxTaxeIRDto> findAll() {

        List<TauxTaxeIR> all = tauxTaxeIRFacade.findAll();
        return tauxTaxeIRConverter.toDto(all);
    }

    @PostMapping("/")

    public int save(@RequestBody TauxTaxeIRDto tauxTaxeIRDto) {
        TauxTaxeIR tauxTaxeIR1 = tauxTaxeIRConverter.toItem(tauxTaxeIRDto);
        return tauxTaxeIRFacade.save(tauxTaxeIR1);
    }


    @GetMapping("/salaireNet/{salaireNet}")
    public TauxTaxeIRDto findBySalaireMaxAndSalaireMin(@PathVariable double salaireNet) {
        TauxTaxeIR taxeIR = tauxTaxeIRFacade.findBySalaireMaxAndSalaireMin(salaireNet);
        return tauxTaxeIRConverter.toDto(taxeIR);
    }
@DeleteMapping("/pourcentage/{pourcentage}")
    public int deleteByPourcentage(@PathVariable double pourcentage) {
        return tauxTaxeIRFacade.deleteByPourcentage(pourcentage);
    }

    @GetMapping("/pourcentage/{pourcentage}")
    public List <TauxTaxeIRDto> findByPourcentage(@PathVariable double pourcentage) {
        List<TauxTaxeIR> taxeIR2 = tauxTaxeIRFacade.findByPourcentage(pourcentage);
        return tauxTaxeIRConverter.toDto(taxeIR2);


    }
}