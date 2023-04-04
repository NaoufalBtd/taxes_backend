package taxes.ws.facade;

import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import taxes.bean.Employe;
import taxes.bean.Societe;
import taxes.bean.TaxeIR;
import taxes.service.facade.TaxeIRFacade;
import taxes.ws.converter.TaxeIRConverter;
import taxes.ws.dto.ResStatDto;
import taxes.ws.dto.TaxeIRDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/TaxeIR")
@RestController
 
public class TaxeIRRest {
    @Autowired
    private TaxeIRFacade taxeIRFacade;

    @Autowired
    private TaxeIRConverter taxeIRConverter;

//    @GetMapping("/societe/{societe}/dateDeclaration/{dateDeclaration}")
//    public List<TaxeIRDto> findBySocieteAndDateDeclaration(@RequestBody Societe societe, @PathVariable Date dateDeclaration) {
//        List<TaxeIR> taxeIRS = taxeIRFacade.findBySocieteAndDateDeclaration(societe, dateDeclaration);
//        return taxeIRConverter.toDto(taxeIRS);
//    }

    @GetMapping("/mois/{mois}/annee/{annee}/ice/{ice}")
    public TaxeIRDto findByMoisAndAnneeAndSocieteIce(@PathVariable int mois, @PathVariable int annee,@PathVariable String ice) {
        TaxeIR taxeIR = taxeIRFacade.findByMoisAndAnneeAndSocieteIce(mois, annee, ice);
        return taxeIRConverter.toDto(taxeIR);    }

    @GetMapping("/")
    public List<TaxeIRDto> findAll(@PathParam("page") Integer page, @PathParam("size") Integer size) {
        Integer pageSize = size == null ? 5 : size; // default page size is 5
        Integer currentPage = page == null ? 0 : page; // default page number is 0

        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        List<TaxeIR> all = taxeIRFacade.findAll(pageRequest);
        List<TaxeIRDto>  dtos = all.stream().map(taxeIRConverter::toDto).collect(Collectors.toList());

        return dtos;
    }

    @PostMapping("/")
    public int save(@RequestBody TaxeIRDto taxeIRDto) {
        TaxeIR taxeIR =taxeIRConverter.toItem(taxeIRDto);
        Optional<List<Employe>> employes = Optional.ofNullable(taxeIRDto.getEmployes());
        if(employes.isPresent())
            return taxeIRFacade.declareTaxeIR(taxeIR, employes.get());
        else
            return taxeIRFacade.declareTaxeIR(taxeIR);
    }

    @GetMapping("/annee/{annee}")
    public List<ResStatDto> calculStatic(@PathVariable int annee) {

        return taxeIRFacade.calculStatic(annee);
    }

    @PutMapping("/updateTaxeIR/taxeIR/{taxeIR}")
    public int updateTaxeIR(@RequestBody TaxeIRDto taxeIRDto) {
        TaxeIR taxeIR =taxeIRConverter.toItem(taxeIRDto);
        return taxeIRFacade.updateTaxeIR(taxeIR);
    }

    @DeleteMapping("/mois/{mois}/annee/{annee}/ice/{ice}")
    public int deleteByMoisAndAnneeAndSocieteIce(@PathVariable int mois,@PathVariable int annee,@PathVariable String ice) {
        return taxeIRFacade.deleteByMoisAndAnneeAndSocieteIce(mois, annee, ice);
    }
}
