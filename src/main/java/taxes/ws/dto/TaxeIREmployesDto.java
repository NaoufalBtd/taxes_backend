package taxes.ws.dto;

import taxes.bean.Employe;
import taxes.bean.TauxTaxeIR;
import taxes.bean.TaxeIR;
import lombok.Data;

@Data
public class TaxeIREmployesDto {


    private Long id;

    private TauxTaxeIR tauxTaxeIR;

    private TaxeIR taxeIR;
    private double salaireNet;
    private double salaireBrute;
    private double montantIR;
    private Boolean employed ;

    private Employe employe;
}
