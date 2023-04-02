package taxes.ws.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResStatDto {

    private int mois ;
    private int annee ;
    private BigDecimal mt ;


    public ResStatDto(int mois, int annee, BigDecimal mt) {
        this.mois = mois;
        this.annee = annee;
        this.mt = mt;
    }
}

