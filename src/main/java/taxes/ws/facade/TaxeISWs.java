package taxes.ws.facade;


import taxes.bean.*;
import taxes.service.facade.TaxeIsFacade;
import taxes.service.impl.TaxeIsService;
import taxes.ws.converter.ISItemConverter;
import taxes.ws.converter.TaxeISConverter;
import taxes.ws.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/TaxeIS")

public class TaxeISWs {
    @Autowired
    private TaxeIsService taxeIsService;
    @Autowired
    private TaxeIsFacade taxeIsFacade;
    @Autowired
    private TaxeISConverter taxeISConverter;
    @Autowired
    private ISItemConverter isItemConverter ;

    @GetMapping("/trimestre/{trimestre}/annee/{annee}/ice/{ice}")
    public TaxeISDto findByAnneeAndTrimestreAndSocieteIce(@PathVariable int annee, @PathVariable int trimestre,@PathVariable String ice) {
        TaxeIS byAnneeAndTrimestreAndSocieteIce = taxeIsFacade.findByAnneeAndTrimestreAndSocieteIce(annee, trimestre , ice);
        return taxeISConverter.toDto(byAnneeAndTrimestreAndSocieteIce);
    }
    @GetMapping("/trimestre/{trimestre}/annee/{annee}")
    public TaxeISDto findByAnneeAndTrimestre(@PathVariable int annee, @PathVariable int trimestre) {
        TaxeIS byAnneeAndTrimestre = taxeIsFacade.findByAnneeAndTrimestre(annee, trimestre );
        return taxeISConverter.toDto(byAnneeAndTrimestre);
    }
    @GetMapping("/ice/{ice}")
    public List<TaxeISDto> findBySocieteIce(@PathVariable String ice) {
        List<TaxeIS> findBySocieteIce = taxeIsFacade.findBySocieteIce(ice );
        return taxeISConverter.toDto(findBySocieteIce);
    }

    @DeleteMapping("/trimestre/{trimestre}/annee/{annee}/ice/{ice}")
    public int deleteByTrimestreAndAnneeAndSocieteIce(@PathVariable int trimestre,@PathVariable int annee,@PathVariable String ice) {
        return taxeIsFacade.deleteByAnneeAndTrimestreAndSocieteIce(trimestre, annee, ice);
    }
    @DeleteMapping("/ice/{ice}")
    public int deleteBySocieteIce(@PathVariable String ice) {
        return taxeIsFacade.deleteBySocieteIce(ice);
    }
    @DeleteMapping("/trimestre/{trimestre}/annee/{annee}")
    public int deleteByAnneeAndTrimestre(@PathVariable int trimestre,@PathVariable int annee) {
        return taxeIsFacade.deleteByAnneeAndTrimestre(trimestre, annee);
    }
    @PostMapping("/")
    public int save(@RequestBody ISItemDto iSItemDto) {
        ISItem sav = isItemConverter.toItem(iSItemDto);
        return taxeIsFacade.save(sav);
    }
    @PutMapping("/")
    public int updateTaxeIS(@RequestBody TaxeISDto taxeISDto) {
        TaxeIS update = taxeISConverter.toItem(taxeISDto);
        return taxeIsFacade.updateTaxeIS(update);
    }

    public List<TaxeISDto> findAll() {
        List<TaxeIS> all = taxeIsFacade.findAll();
        return  taxeISConverter.toDto(all);
    }

@GetMapping("/annee/{annee}")
    public List<ResStatiqueISDto> calcStatique(int annee) {
        return taxeIsService.calcStatique(annee);
    }
}

