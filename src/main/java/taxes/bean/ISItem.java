package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ISItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    TaxeIS taxeIS;
    @OneToMany
    List<FactureGagne>  factureGagnes ;
    @OneToMany
    List<FacturePerte> facturePertes;
}
