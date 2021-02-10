package org.vacation.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDto {

    private Long notificationId;
    private Long source = 999l;
    private Long destination;
    private Date date;
    private boolean seen = false;
    private String message;

}
