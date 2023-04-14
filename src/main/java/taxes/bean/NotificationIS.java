package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class NotificationIS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int trim;
    private int trimMin;
    private int trimMax;

    private int annee;
    private int anneeMax;
    private int anneeMin;
    @ManyToOne
    private Societe societe;

    @OneToMany(mappedBy = "notificationIS")
    private List<NotificationISDetail> notificationISDetails;


}