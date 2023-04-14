package taxes.ws.facade;


import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import taxes.bean.*;
import taxes.service.facade.IsItemFacade;
import taxes.service.facade.TaxeIsFacade;
import taxes.service.impl.TaxeIsService;
import taxes.ws.converter.ISItemConverter;
import taxes.ws.converter.TaxeISConverter;
import taxes.ws.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/TaxeIS")

public class TaxeISWs {
    @Autowired
    private TaxeIsService taxeIsService;
    @Autowired
    private TaxeIsFacade taxeIsFacade;
    @Autowired
    private IsItemFacade isItemFacade;
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
    @GetMapping("/undeclared")
    public List<TaxeISDto> findUndeclared(@AuthenticationPrincipal User user) {
        List<TaxeIS> undeclared = taxeIsFacade.findUndeclaredTaxes(user.getSociete().getIce());
        return undeclared.stream().map(taxeISConverter::toDto).collect(Collectors.toList());
    }

    @GetMapping("/")
    public List<TaxeISDto> findBySocieteIce(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<TaxeIS> taxes = taxeIsFacade.findBySocieteIce(user.getSociete().getIce(), pageRequest);
        return taxes.stream().map(taxeISConverter::toDto).collect(Collectors.toList());
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
    public int save(@RequestBody TaxeISDto taxeISDto, @AuthenticationPrincipal User user) {
        TaxeIS tax = taxeISConverter.toItem(taxeISDto);
        return taxeIsFacade.declareTax( tax, user.getSociete());
    }
    @PutMapping("/")
    public int updateTaxeIS(@RequestBody TaxeISDto taxeISDto) {
        TaxeIS update = taxeISConverter.toItem(taxeISDto);
        return taxeIsFacade.updateTaxeIS(update);
    }

    @PostMapping("/add")
    public int saveTaxesISByTrimester(@RequestBody TaxeISDto taxeISDto) {
        return taxeIsFacade.saveTaxesISByTrimester( taxeISDto.getAnnee(), taxeISDto.getTrimestre());
    }


@GetMapping("/annee/{annee}")
    public List<ResStatiqueISDto> calcStatique(int annee) {
        return taxeIsService.calcStatique(annee);
    }
}

