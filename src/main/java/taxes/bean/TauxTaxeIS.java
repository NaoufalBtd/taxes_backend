package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
public class TauxTaxeIS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private Date dateApplicationDebut ;

    private Date dateApplicationFin ;
    private double pourcentage ;
   //foat --> double
   private  double resultMax ;
    private double resultatMin ;


}
