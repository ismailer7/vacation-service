package org.vacation.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vacation.beans.NotificationDto;
import org.vacation.models.Notification;
import org.vacation.repositories.INotificationRepository;
import org.vacation.services.impl.NotificationServiceImpl;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private INotificationRepository notificationRepository;

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public List<Notification> checkForNotifications(@PathVariable("userId") Long userId) {
        // this end point will be checked regularly from the client.
        return notificationRepository.findAllByDestinationOrderByDateAsc(userId);
        // return "There are some notifications for user with ID = '" + userId + "' not yet consumed!";
    }

    @RequestMapping(value = "/add/{userId}/{msg}", method = RequestMethod.POST)
    public void addNotification(@PathVariable("userId") Long userId, @PathVariable("msg") String message) {
        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setDestination(userId);
        notification.setMessage("This is a notification!");
        notificationRepository.save(notification);
    }
}
