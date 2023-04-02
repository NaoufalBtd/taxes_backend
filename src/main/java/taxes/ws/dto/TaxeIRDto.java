package taxes.ws.dto;

import taxes.bean.Societe;
import taxes.bean.TauxTaxeIR;
import taxes.bean.TaxeIREmployes;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaxeIRDto {
    private Long id;
    private int mois;
    private int annee;


    private TauxTaxeIR tauxTaxeIR;

    private Societe societe;
    private Date dateDeclaration;//societe declare

    private double salaireNet;//total des salaires net de tout les employes
    private double salaireBrute;
    private double montantIR;
    private List<TaxeIREmployes> taxeIREmployes;

}
