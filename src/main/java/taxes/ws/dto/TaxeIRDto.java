package taxes.ws.dto;

import taxes.bean.Employe;
import taxes.bean.Societe;
import taxes.bean.TauxTaxeIR;
import taxes.bean.TaxeIREmployes;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class TaxeIRDto {
    private Long id;
    private int mois;
    private int annee;


    private TauxTaxeIR tauxTaxeIR;

    private Societe societe;
    private String dateDeclaration;//societe declare

    private double salaireNet;//total des salaires net de tout les employes
    private double salaireBrute;
    private double montantIR;
    private List<TaxeIREmployes> taxeIREmployes;
    private List<Employe> employes;

}
