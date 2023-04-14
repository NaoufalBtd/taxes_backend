package taxes.service.facade;

import taxes.bean.NotificationISDetail;
import taxes.bean.Societe;

import java.util.List;

public interface NotificationISDetailFacade{
    int saveNotificationDetailBySociete(Societe societe);

    List<NotificationISDetail> findActiveNotificationBySociete(Societe societe);
}
