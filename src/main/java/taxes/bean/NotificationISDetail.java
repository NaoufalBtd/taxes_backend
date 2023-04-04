package taxes.bean;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class NotificationISDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    private NotificationIS notificationIS;

    @ManyToOne
    private Societe societe;

}