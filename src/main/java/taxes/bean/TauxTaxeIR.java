package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class TauxTaxeIR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateApplicationDebut;
    @Temporal(TemporalType.DATE)
    private Date dateApplicationFin;
    private double pourcentage;
    private Double salaireMax;

    private double salaireMin;



}
