package taxes.ws.dto;

import taxes.bean.FactureGagne;
import taxes.bean.FacturePerte;
import taxes.bean.TaxeIS;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class ISItemDto {

    private Long id;

    TaxeIS taxeIS;
    List<FactureGagne> factureGagnes ;

    List<FacturePerte> facturePertes;
}
