package org.vacation.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.vacation.beans.UserDto;
import org.vacation.models.History;
import org.vacation.models.User;
import org.vacation.models.Vacation;
import org.vacation.beans.VacationDto;
import org.vacation.repositories.IHistoryRepository;
import org.vacation.repositories.IUserRepository;
import org.vacation.repositories.IVacationRepository;
import org.vacation.services.ICRUDService;
import org.vacation.services.IUserService;
import org.vacation.transformers.Transformer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VacationCRUDServiceImpl implements ICRUDService<VacationDto, Long> {

	@Autowired
	private IVacationRepository vacationRepository;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private Transformer<VacationDto, Vacation> vacationTransformer;

	@Autowired
	private IHistoryRepository historyRepository;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");

	@Override
	public VacationDto create(VacationDto vacationDto) {
		/**
		 * TODO - raise an exception in case user id is missing.
		 */
		Vacation vacation = vacationTransformer.toEntity(vacationDto);
		// save vacation.
		Vacation vacationResult = vacationRepository.saveAndFlush(vacation);
		if(vacationResult != null) {
			History history = new History(History.HISTORY_CREATED_MSG, new Date(), vacationDto.getUserId(), vacation);
			historyRepository.save(history);
		}
		return vacationTransformer.toDto(vacationResult);
	}

	@Override
	public VacationDto getById(Long vacationId) {
		Vacation result = vacationRepository.getOne(vacationId);
		VacationDto dto = null;
		if (result != null) {
			dto = vacationTransformer.toDto(result);
		} else {
			raiseException(String.format("No vacation found with id = %l", vacationId));
		}
		return dto;
	}

	@Override
	public VacationDto update(Long id, VacationDto vacationDto) {
		Vacation vacationToBeUpdated = vacationRepository.getOne(id);
		Vacation vacationUpdated = null;
		if(vacationToBeUpdated != null) {
			vacationUpdated = updateVacation(vacationToBeUpdated, vacationDto);
			// save - if the id exist in db then it will update not insert.
			Vacation result = vacationRepository.save(vacationUpdated);
			if(result != null) {
				// save
				String actionBy = vacationDto.getAssignment();
				Long userId = userService.findByUsername(actionBy).getId(); // current assignment.
				History history = new History(History.HISTORY_UPDATE_MSG, new Date(), userId, vacationToBeUpdated);
				historyRepository.save(history);
				return vacationTransformer.toDto(result);
			}
		} else {
			raiseException(String.format("No vacation found with id = %l", id));
		}
		return null;
	}

	@Override
	public void delete(Long vacationId) {
		try {
			Vacation v = vacationRepository.getOne(vacationId);
			Long userId = userService.findByUsername(v.getAssignment()).getId();
			vacationRepository.deleteById(vacationId);
			History history = new History(History.HISTORY_UPDATE_MSG, new Date(), userId, v);
			historyRepository.save(history);
		} catch (IllegalArgumentException e) {
			raiseException(String.format("No vacation found with id = %l", vacationId));
		}
	}

	/**
	 *
	 * @param vacation
	 * @param dto
	 * @return
	 */
	private Vacation updateVacation(Vacation vacation, VacationDto dto) {
		// id will be the same
		// user will be the same
		// other data can be updated.
		if(dto.getVacationTitle() != null) {
			vacation.setTitle(dto.getVacationTitle());
		}
		return vacation;
	}

	private void raiseException(String message) {
		throw new IllegalStateException(message);
	}

}
