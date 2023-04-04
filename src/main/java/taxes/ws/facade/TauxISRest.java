package taxes.ws.facade;

import taxes.bean.TauxTaxeIS;
import taxes.service.facade.TauxTaxeISFacade;
import taxes.ws.converter.TauxIsConverter;
import taxes.ws.dto.TauxTaxeIsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tauxIs")
public class TauxISRest {
    @Autowired
    TauxTaxeISFacade tauxTaxeISFacade;
    @Autowired
    TauxIsConverter tauxIsConverter;

    @GetMapping("/dateDebut/{dateDebut}/dateFin/{dateFin}")
    public TauxTaxeIsDto findByDateApplicationDebutAndDateApplicationFin(@PathVariable Date dateDebut,@PathVariable Date dateFin) {
        TauxTaxeIS tauxTaxeIS = tauxTaxeISFacade.findByDateApplicationDebutAndDateApplicationFin(dateDebut, dateFin);
        TauxTaxeIsDto tauxTaxeIsDto = tauxIsConverter.toDto(tauxTaxeIS);
        return tauxTaxeIsDto;
    }
    @PutMapping("/")
    public int update(@RequestBody TauxTaxeIS tauxTaxeIS) {
        return tauxTaxeISFacade.update(tauxTaxeIS);
    }

    @DeleteMapping("/dateDebut/{dateDebut}/dateFin/{dateFin}")
    public int deleteByDateApplicationDebutAndDateApplicationFin(@PathVariable Date dateDebut,@PathVariable Date dateFin) {
        return tauxTaxeISFacade.deleteByDateApplicationDebutAndDateApplicationFin(dateDebut, dateFin);
    }

    @GetMapping("/resultatAvantImpotBetweenMinAndMax/{resultatAvantImpot}")
    public TauxTaxeIsDto findByResultatAvantImpotBetweenMinAndMax(@PathVariable double resultatAvantImpot) {
        TauxTaxeIS tauxTaxeIS = tauxTaxeISFacade.findByResultatAvantImpot(resultatAvantImpot);
        TauxTaxeIsDto tauxTaxeIsDto = tauxIsConverter.toDto(tauxTaxeIS);
        return tauxTaxeIsDto;
    }
    @PostMapping("/")
    public int save(@RequestBody TauxTaxeIS tauxTaxeIS) {
        return tauxTaxeISFacade.save(tauxTaxeIS);
    }
    @GetMapping("/")
    public List<TauxTaxeIS> findAll() {
        return tauxTaxeISFacade.findAll();
    }
}
