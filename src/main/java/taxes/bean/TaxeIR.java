package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class TaxeIR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int mois;
    private int annee;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private TauxTaxeIR tauxTaxeIR;
    @ManyToOne
    private Societe societe;
    @Temporal(TemporalType.DATE)
    private Date dateDeclaration;//societe declare

    private double salaireNet;//total des salaires net de tout les employes
    private double salaireBrute;
    private double montantIR;
//    @OneToMany(mappedBy = "taxeIR", fetch = FetchType.LAZY)
//    private List<TaxeIREmployes> taxeIREmployes;

}
