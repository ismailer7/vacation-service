package org.vacation.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VacationDto {
	
	@JsonProperty("vacation_id")
	private Long vacationId;
	
	@JsonProperty("vacation_title")
	private String vacationTitle;

	@JsonProperty("start_date")
	private String startDate;

	@JsonProperty("end_date")
	private String endDate;

	@JsonProperty("status")
	private int status;

	@JsonProperty("user_id")
	private Long userId;

	@JsonProperty("assigned_to")
	private String assignment;
	
}
