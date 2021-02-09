package org.vacation.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentBean {

    @JsonProperty(value = "vacation_id", required = true)
    private Long vacationId;

    @JsonProperty(value = "assign_to", required = true)
    private String assignment; // username.

}
