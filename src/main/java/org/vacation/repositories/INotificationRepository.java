package org.vacation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vacation.models.Notification;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long>  {

    List<Notification> findByDestination(Long destination);

    List<Notification> findAllByDestinationOrderByDateAsc(Long destination);

}
