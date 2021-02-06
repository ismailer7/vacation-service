package org.vacation.services;

import org.vacation.beans.VacationDto;
import org.vacation.filters.VacationFilter;

import java.util.List;

public interface IVacationService {
	
	// other specific services.
	
	byte[] extractVacation(Long vacationId, int type);

	List<VacationDto> filter(VacationFilter vacationfilter);
}
