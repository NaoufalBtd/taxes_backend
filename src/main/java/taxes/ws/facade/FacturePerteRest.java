package taxes.ws.facade;

import taxes.bean.FactureGagne;
import taxes.bean.FacturePerte;
import taxes.service.facade.FacturePerteFacade;
import taxes.ws.converter.FacturePerteConverter;
import taxes.ws.dto.FactureGagneDto;
import taxes.ws.dto.FacturePerteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Factureperte")
public class FacturePerteRest {
    @Autowired
    private FacturePerteFacade facturePerteFacade;
    @Autowired
    private FacturePerteConverter facturePerteConverter;



    @PostMapping("/")
    public int save(@RequestBody FacturePerteDto facturePerteDto) {
        FacturePerte save = facturePerteConverter.toItem(facturePerteDto);
        return facturePerteFacade.save(save);
    }

}