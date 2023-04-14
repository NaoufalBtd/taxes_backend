package taxes.pojos;

import lombok.Getter;
import lombok.Setter;
import taxes.enums.NotificationSeverityLevel;

@Setter
@Getter
public class NotificationContent {
    private String message;
    private NotificationSeverityLevel severityLevel;
}

