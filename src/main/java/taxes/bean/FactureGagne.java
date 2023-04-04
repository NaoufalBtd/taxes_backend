package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class FactureGagne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;

    private double montantHT;
    private double montantTTC;
    private double tva;
    private Date dateFacture;

    @ManyToOne
    private Societe societe;
    @ManyToOne
    private TaxeIS taxeIS;

}