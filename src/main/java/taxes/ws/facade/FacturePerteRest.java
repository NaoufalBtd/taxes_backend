package taxes.ws.facade;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import taxes.bean.FacturePerte;
import taxes.bean.FacturePerte;
import taxes.bean.SumInvoicesByMonth;
import taxes.bean.User;
import taxes.service.facade.FacturePerteFacade;
import taxes.ws.converter.FacturePerteConverter;
import taxes.ws.dto.FacturePerteDto;
import taxes.ws.dto.FacturePerteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Factureperte")
public class FacturePerteRest {
    @Autowired
    private FacturePerteFacade facturePerteFacade;
    @Autowired
    private FacturePerteConverter facturePerteConverter;

    @GetMapping("/")
    public List<FacturePerteDto> findAll(@AuthenticationPrincipal User user, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(name = "size", required = false, defaultValue = "4") Integer size, @RequestParam(name = "startDate", required = false) String startDate, @RequestParam(name = "endDate", required = false) String endDate) throws ParseException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("dateFacture").descending());
        List<FacturePerte> facturePertes;
        if(startDate != null && endDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = dateFormat.parse(startDate);
            Date eDate = dateFormat.parse(endDate);
            facturePertes = facturePerteFacade.findBySocieteAndDate(user.getSociete().getIce(), sDate, eDate, pageRequest);
        } else {
            facturePertes = facturePerteFacade.findBySociete(user.getSociete().getIce(), pageRequest);
        }
        return facturePerteConverter.toDto(facturePertes);
    }


    @PostMapping("/")
    public int save(@RequestBody FacturePerteDto facturePerteDto) {
        FacturePerte save = facturePerteConverter.toItem(facturePerteDto);
        return facturePerteFacade.save(save);
    }

    @PutMapping("/{id}")
    public int update(@PathVariable Long id, @RequestBody FacturePerteDto facturePerteDto, @AuthenticationPrincipal User user) {
        facturePerteDto.setSociete(user.getSociete());
        FacturePerte save = facturePerteConverter.toItem(facturePerteDto);
        return facturePerteFacade.update(save);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return facturePerteFacade.delete(id);
    }

    @GetMapping("/statistics")
    public List<SumInvoicesByMonth> sum(@AuthenticationPrincipal User user, @RequestParam(name = "monthsAgo", required = false, defaultValue = "6") Long monthsAgo ) {
        return facturePerteFacade.getIncomeInvoicesSumByMonth(user.getSociete().getIce(), monthsAgo).stream().map(objects -> {
            SumInvoicesByMonth sumInvoicesByMonth = new SumInvoicesByMonth();
            sumInvoicesByMonth.setMonth((int) objects[0]);
            sumInvoicesByMonth.setYear((int) objects[1]);
            sumInvoicesByMonth.setSumTTC((double) objects[2]);
            return sumInvoicesByMonth;
        }).collect(Collectors.toList());


    }
}