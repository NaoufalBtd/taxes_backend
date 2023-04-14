package taxes.ws.dto;

import taxes.bean.Societe;
import taxes.bean.TaxeIS;
import lombok.Data;

import java.util.Date;
@Data
public class FacturePerteDto {

    private Long id;
    private String code;

    private double montantHT;

    private double montantHC;

    private double montantTTC;

    private double tva;

    private Date dateFacture;

    private Societe societe;

    private TaxeIS taxeIS;


}
