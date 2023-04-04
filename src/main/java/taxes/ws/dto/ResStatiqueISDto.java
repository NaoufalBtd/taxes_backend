package taxes.ws.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResStatiqueISDto {

    private int trimestre;
    private int annee ;
    private BigDecimal montantIs ;


    public ResStatiqueISDto(int trimestre, int annee, BigDecimal montantIs) {
        this.trimestre= trimestre;
        this.annee = annee;
        this.montantIs= montantIs;
    }
}