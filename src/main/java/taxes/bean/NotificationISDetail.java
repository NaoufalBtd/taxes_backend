package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;
import taxes.enums.NotificationSeverityLevel;

@Data
@Entity
public class NotificationISDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationSeverityLevel severityLevel;


    @ManyToOne
    private NotificationIS notificationIS;

    @ManyToOne
    private Societe societe;
    @ManyToOne
    private TaxeIS taxeIS;

}