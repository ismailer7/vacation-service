package org.vacation.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vacation.filters.VacationFilter;
import org.vacation.beans.VacationDto;
import org.vacation.services.impl.VacationServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/vacation")
public class VacationController {

	@Autowired
	private VacationServiceImpl vacationService;

	@GetMapping("/test")
	public String anotherTest() {
		return "test vacation";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public VacationDto createVacationForm(@RequestBody VacationDto vacationForm) {
			return vacationService.create(vacationForm);
	}

	@RequestMapping(value = "/modify/{vacationId}", method = RequestMethod.PUT)
	public VacationDto modifyVacation(@PathVariable("vacationId") Long vacationId, @RequestBody VacationDto vacationForm) {
		return vacationService.update(vacationId, vacationForm);
	}

	@RequestMapping(value = "/remove/{vacationId}", method = RequestMethod.DELETE)
	public void removeVacation(@PathVariable("vacationId") Long vacationId) {
		vacationService.delete(vacationId);
	}

	@RequestMapping(value = "/{vacationId}", method = RequestMethod.GET)
	public VacationDto getVacationById(@PathVariable("vacationId") Long vacationId) {
		return vacationService.getById(vacationId);
	}

	@RequestMapping(value = "/vacations", method = RequestMethod.GET)
	public List<VacationDto> retrieveVacations(@RequestBody VacationFilter vacationFilter) {
		/**
		 * TODO add retrieve feature.
		 */
		return vacationService.filter(vacationFilter);
	}

	@RequestMapping(value = "/extract/{type}/{vacationId}", method = RequestMethod.GET)
	public byte[] extractVacation(@PathVariable("type") int type, @PathVariable("vacationId") Long vacationId) {
		return vacationService.extractVacation(vacationId, type);
	}

}
