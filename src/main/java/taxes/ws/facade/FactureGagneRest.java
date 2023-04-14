package taxes.ws.facade;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import taxes.bean.*;
import taxes.service.facade.FactureGagneFacade;
import taxes.ws.converter.FactureGagneConverter;
import taxes.ws.dto.FactureGagneDto;
import taxes.ws.dto.ISItemDto;
import taxes.ws.dto.TaxeISDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/FactureGagne")
public class FactureGagneRest {

    @Autowired
    private FactureGagneFacade factureGagneFacade;
    @Autowired
    FactureGagneConverter factureGagneConverter;

    @GetMapping("/")
    public List<FactureGagneDto> findAll(@AuthenticationPrincipal User user, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(name = "size", required = false, defaultValue = "4") Integer size, @RequestParam(name = "startDate", required = false) String startDate, @RequestParam(name = "endDate", required = false) String endDate) throws ParseException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("dateFacture").descending());
        List<FactureGagne> factureGagnes;
        if(startDate != null && endDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = dateFormat.parse(startDate);
            Date eDate = dateFormat.parse(endDate);
            factureGagnes = factureGagneFacade.findBySocieteAndDate(user.getSociete().getIce(), sDate, eDate, pageRequest);
        } else {
            factureGagnes = factureGagneFacade.findBySociete(user.getSociete().getIce(), pageRequest);
        }
        return factureGagneConverter.toDto(factureGagnes);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return factureGagneFacade.delete(id);
    }

    @PostMapping("/")
    public int save(@RequestBody FactureGagneDto factureGagneDto, @AuthenticationPrincipal User user) {
        factureGagneDto.setSociete(user.getSociete());
        FactureGagne save = factureGagneConverter.toItem(factureGagneDto);
        return factureGagneFacade.save(save);
    }

    @PutMapping("/{id}")
    public int update(@RequestBody FactureGagneDto factureGagneDto, @PathVariable Long id, @AuthenticationPrincipal User user) {
        factureGagneDto.setSociete(user.getSociete());
        FactureGagne save = factureGagneConverter.toItem(factureGagneDto);
        return factureGagneFacade.update(save);
    }

    @GetMapping("/statistics")
    public List<SumInvoicesByMonth> sum(@AuthenticationPrincipal User user, @RequestParam(name = "monthsAgo", required = false, defaultValue = "6") Long monthsAgo ) {
        return factureGagneFacade.getIncomeInvoicesSumByMonth(user.getSociete().getIce(), monthsAgo).stream().map(objects -> {
            SumInvoicesByMonth sumInvoicesByMonth = new SumInvoicesByMonth();
            sumInvoicesByMonth.setMonth((int) objects[0]);
            sumInvoicesByMonth.setYear((int) objects[1]);
            sumInvoicesByMonth.setSumTTC((double) objects[2]);
            return sumInvoicesByMonth;
        }).collect(Collectors.toList());
    }


}
