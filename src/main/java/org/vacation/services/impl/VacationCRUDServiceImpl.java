package org.vacation.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.vacation.beans.UserDto;
import org.vacation.models.User;
import org.vacation.models.Vacation;
import org.vacation.beans.VacationDto;
import org.vacation.repositories.IUserRepository;
import org.vacation.repositories.IVacationRepository;
import org.vacation.services.ICRUDService;
import org.vacation.services.IUserService;
import org.vacation.transformers.Transformer;

public class VacationCRUDServiceImpl implements ICRUDService<VacationDto, Long> {

	@Autowired
	private IVacationRepository vacationRepository;

	@Autowired
	private IUserService userService;

	@Autowired
	private Transformer<VacationDto, Vacation> vacationTransformer;

	@Override
	public VacationDto create(VacationDto vacationDto) {
		/**
		 * TODO - raise an exception in case user id is missing.
		 */
		Vacation vacation = vacationTransformer.toEntity(vacationDto);
		// save vacation.
		Vacation vacationResult = vacationRepository.saveAndFlush(vacation);
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
				// save successfull
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
			vacationRepository.deleteById(vacationId);
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
