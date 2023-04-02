package taxes.ws.dto;

import taxes.bean.Societe;
import jakarta.persistence.*;
import lombok.Data;


@Data

public class EmployeDto {
    private Long id;

    private String cin;

    private String nom;

    private Societe societe;

    private String prenom;
    private double salaire;


}
