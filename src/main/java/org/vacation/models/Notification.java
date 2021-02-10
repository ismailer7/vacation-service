package org.vacation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTIFICATION_QUEUE")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    @Column(name = "SOURCE")
    private Long source = 999l; // user or system.

    @Column(name = "DESTINATION")
    private Long destination; // which user we need to notify.

    @Column(name = "time")
    private Date date;

    @Column(name = "SEEN")
    private boolean seen = false;

    @Column(name = "MSG")
    private String message;

}
