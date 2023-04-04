package taxes.ws.facade;

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


    @PostMapping("/")
    public int save(@RequestBody FactureGagneDto factureGagneDto, @AuthenticationPrincipal User user) {
        factureGagneDto.setSociete(user.getSociete());
        FactureGagne save = factureGagneConverter.toItem(factureGagneDto);
        return factureGagneFacade.save(save);
    }


}
