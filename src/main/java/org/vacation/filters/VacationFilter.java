package org.vacation.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class VacationFilter {

    @JsonProperty("created_by")
    public String createdBy;

    @JsonProperty("start_date")
    public Date startDate;

    @JsonProperty("end_date")
    public Date endDate;

    @JsonProperty("status")
    private int status;
}
