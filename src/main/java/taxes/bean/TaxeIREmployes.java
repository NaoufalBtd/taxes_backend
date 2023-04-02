package taxes.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TaxeIREmployes {
    public TaxeIREmployes() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)


    private Long id;

    @ManyToOne
    private TauxTaxeIR tauxTaxeIR;
    @ManyToOne
    @JsonBackReference
    private TaxeIR taxeIR;
    private double salaireNet;
    private double salaireBrute;
    private double montantIR;
    private Boolean employed ;

    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Societe societe;
}
