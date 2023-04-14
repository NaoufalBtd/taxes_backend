package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class FacturePerte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    private double montantHT;
    private double montantHC;
    private double montantTTC;
    private double tva;
    private Date dateFacture;

    @ManyToOne
    private Societe societe;
    @ManyToOne
    private TaxeIS taxeIS;

}