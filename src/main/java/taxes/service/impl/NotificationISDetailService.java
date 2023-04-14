package taxes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taxes.bean.NotificationISDetail;
import taxes.bean.Societe;
import taxes.bean.TaxeIS;
import taxes.dao.NotificationISDetailDao;
import taxes.enums.NotificationSeverityLevel;
import taxes.pojos.NotificationContent;
import taxes.service.facade.NotificationFacade;
import taxes.service.facade.NotificationISDetailFacade;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationISDetailService implements NotificationISDetailFacade {
    private final NotificationISDetailDao notificationISDetailDao;
    private final TaxeIsService taxeIsService;

    @Override
    public int saveNotificationDetailBySociete(Societe societe) {
        List<TaxeIS> undeclaredTaxes = taxeIsService.findUndeclaredTaxes(societe.getIce());
        for (TaxeIS taxeIS : undeclaredTaxes) {
            NotificationContent content = getMessageAndSeverity(taxeIS);
            NotificationISDetail notificationISDetail = new NotificationISDetail();
            notificationISDetail.setTaxeIS(taxeIS);
            notificationISDetail.setSociete(societe);
            notificationISDetail.setMessage(content.getMessage());
            notificationISDetail.setSeverityLevel(content.getSeverityLevel());
//            notificationISDetail.setNotification(notificationFacade.findActiveNotificationBySociete(ice));
//            notificationISDetail.setIsDeclared(false);
            notificationISDetailDao.save(notificationISDetail);
        }
        return 1;

    }

    @Override
    public List<NotificationISDetail> findActiveNotificationBySociete(Societe societe) {
        return notificationISDetailDao.findActiveNotificationBySociete(societe);
    }

    private NotificationContent getMessageAndSeverity(TaxeIS taxeIS) {
        NotificationContent notificationContent = new NotificationContent();
        Date dueDate = taxeIS.getDateEcheance();
        long daysBetween = ChronoUnit.DAYS.between(dueDate.toInstant(), new Date().toInstant());
        if(daysBetween <= 0) {
            String msg = "You have new IS tax to be paid before " + dueDate.getMonth() + " " + dueDate.getYear();
            notificationContent.setMessage(msg);
            notificationContent.setSeverityLevel(NotificationSeverityLevel.INFO);
        } else if(daysBetween < 30) {
            String msg = "IS tax with id #IS-" + taxeIS.getId() + " will increase by 10% will apply " + dueDate.getMonth() + 1 + " " + dueDate.getYear();
            notificationContent.setMessage(msg);
            notificationContent.setSeverityLevel(NotificationSeverityLevel.WARNING);
        } else {
             String msg = "10% increase has been applied to IS Tax with id #IS-" + taxeIS.getId();
            notificationContent.setMessage(msg);
            notificationContent.setSeverityLevel(NotificationSeverityLevel.DANGER);
        }
        return notificationContent;
    }
}
