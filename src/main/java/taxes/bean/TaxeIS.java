package taxes.bean;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class TaxeIS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int annee;

    private int trimestre;
    @ManyToOne
    private Societe societe;

    private double ChiffreAffaire;
    private double charge;
    private Date DateEcheance;
    private Date DatePaiement;

    private double resultatAvantImpot;
    private double resultatApresImpot;
    private double montantIs;


    private boolean declared = false;


    @ManyToOne
    private TauxTaxeIS tauxTaxeIS;
    @OneToMany(mappedBy = "taxeIS")
    @ToString.Exclude
    private List<NotificationISDetail> notificationISDetail;
    @OneToMany
    @ToString.Exclude
    List<FactureGagne>  factureGagnes ;
    @OneToMany
    @ToString.Exclude
    List<FacturePerte> facturePertes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaxeIS taxeIS = (TaxeIS) o;
        return getId() != 0 && Objects.equals(getId(), taxeIS.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    public boolean getDeclared() {
        return this.declared;
    }
}
