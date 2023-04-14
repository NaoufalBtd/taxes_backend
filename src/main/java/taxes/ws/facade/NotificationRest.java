package taxes.ws.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taxes.bean.NotificationISDetail;
import taxes.bean.User;
import taxes.service.facade.NotificationFacade;
import taxes.service.facade.NotificationISDetailFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationRest {
    private final NotificationISDetailFacade notificationFacade;

    @PostMapping("/save")
    public int saveNotificationDetail(@AuthenticationPrincipal User user) {
        return notificationFacade.saveNotificationDetailBySociete(user.getSociete());
    }

    @GetMapping("/")
    public List<NotificationISDetail> findActiveNotificationBySociete(@AuthenticationPrincipal User user) {
        return notificationFacade.findActiveNotificationBySociete(user.getSociete());
    }
}
