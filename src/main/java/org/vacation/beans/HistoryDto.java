package org.vacation.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDto {

    private Long historyId;

    private String message;

    private Date time;

    private Long actionBy;

    private Long vacationId;
}
