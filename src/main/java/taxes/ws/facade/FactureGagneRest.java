package taxes.ws.facade;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import taxes.bean.FactureGagne;
import taxes.bean.ISItem;
import taxes.bean.TaxeIS;
import taxes.bean.User;
import taxes.service.facade.FactureGagneFacade;
import taxes.ws.converter.FactureGagneConverter;
import taxes.ws.dto.FactureGagneDto;
import taxes.ws.dto.ISItemDto;
import taxes.ws.dto.TaxeISDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/v1/FactureGagne")
public class FactureGagneRest {

    @Autowired
    private FactureGagneFacade factureGagneFacade;
    @Autowired
    FactureGagneConverter factureGagneConverter;

    @GetMapping("/")
    public List<FactureGagneDto> findAll(@AuthenticationPrincipal User user, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(name = "size", required = false, defaultValue = "4") Integer size, @RequestParam(name = "startDate", required = false) Date startDate, @RequestParam(name = "endDate", required = false) Date endDate) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("dateFacture").descending());
        List<FactureGagne> factureGagnes;
        if(startDate != null && endDate != null) {
            factureGagnes = factureGagneFacade.findBySocieteAndDate(user.getSociete().getIce(), startDate, endDate, pageRequest);
        } else {
            factureGagnes = factureGagneFacade.findBySociete(user.getSociete().getIce(), pageRequest);
        }
        return factureGagneConverter.toDto(factureGagnes);
    }


    @PostMapping("/")
    public int save(@RequestBody FactureGagneDto factureGagneDto, @AuthenticationPrincipal User user) {
        factureGagneDto.setSociete(user.getSociete());
        FactureGagne save = factureGagneConverter.toItem(factureGagneDto);
        return factureGagneFacade.save(save);
    }

    @GetMapping("/sum")
    public List<Object[]> sum(@AuthenticationPrincipal User user) {
        return factureGagneFacade.getLastSixMonthsIncomeInvoicesSumPerMonth(user.getSociete().getIce());
    }


}
