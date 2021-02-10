package org.vacation.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "VACATION_HISTORY")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class History {

    public final static int SYSTEM_ACTION = 300;

    public final static String HISTORY_CREATED_MSG = "The Vacation Form has been Created";
    public final static String HISTORY_UPDATE_MSG = "Form Vacation Updated";
    public final static String HISTORY_APPROVED_MSG = "Form Vacation has been Approved";
    public final static String HISTORY_REJECTED_MSG = "Form Vacation has been Rejected";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long historyId;

    @Column(name = "MESSAGE", length = 250, nullable = false)
    private String message;

    @Column(name = "ACTION_DATE", nullable = false)
    private Date time;

    @Column(name = "ACTION_BY")
    private Long actionBy; // or system -> for example 300

    @ManyToOne
    private Vacation vacation;

    public History(String historyMsg, Date time, Long actionBy, Vacation vacation) {
        this.message = historyMsg;
        this.time = time;
        this.actionBy = actionBy;
        this.vacation = vacation;
    }
}
