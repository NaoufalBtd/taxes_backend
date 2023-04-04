package taxes.ws.dto;

import lombok.Data;

import java.util.Date;


@Data

public class TauxTaxeIsDto {
    private Long id ;

    private Date dateApplicationDebut ;

    private Date dateApplicationFin ;
    private double pourcentage ;

    private  double resultMax ;
    private double resultatMin ;




}
