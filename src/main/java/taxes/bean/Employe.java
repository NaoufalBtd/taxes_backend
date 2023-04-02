package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity

public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cin;

    private String nom;

    @ManyToOne
    private Societe societe;

    private String prenom;
    private double salaire;



}
