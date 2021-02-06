package org.vacation.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VacationFilter {

    @JsonProperty("created_by")
    public String createdBy;

}
