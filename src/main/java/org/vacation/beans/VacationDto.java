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

	@JsonProperty("user_id")
	private Long userId;
	
}
