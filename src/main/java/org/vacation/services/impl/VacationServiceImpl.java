package org.vacation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vacation.beans.VacationDto;
import org.vacation.filters.VacationFilter;
import org.vacation.models.User;
import org.vacation.repositories.IVacationRepository;
import org.vacation.services.IVacationService;
import org.vacation.transformers.VacationTransformerImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class VacationServiceImpl extends VacationCRUDServiceImpl implements IVacationService {

	@Autowired
	private IVacationRepository vacationRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private VacationTransformerImpl vacationTransformer;

	@Override
	public byte[] extractVacation(Long vacationId, int type) {
		switch(type) {
			case 1:
				// excel.
				break;
			case 2:
				// pdf
				break;
		}
		return null;
	}

	@Override
	public List<VacationDto> filter(VacationFilter vacationfilter) {
		StringBuilder query = new StringBuilder("SELECT * FROM VACATION WHERE 1=1");
		/**
		 * TODO read properties with reflection.
		 * TODO Fix the error.
		 * TODO check case where no value present - maybe we add default values to vacationFilter object.
		 */
		Class<?> c = vacationfilter.getClass();
		for (Field field: c.getDeclaredFields()) {
			String filedName = field.getName();
			String wherePart = "";
			switch(filedName) {
				case "createdBy":
					String createdBy = null;
					try {
						createdBy = (String) field.get(vacationfilter); // is username.
					} catch (IllegalAccessException e) {
						// throw new IllegalStateException("Error while reading filed value: " + field.getName());
						continue;
					}
					if(createdBy != null) {
						User user = userService.findByUsername(createdBy);
						Long createdById = user.getId();
						wherePart = String.format(" AND USER_ID = %s", createdById);
					}
					break;
				case "status":
					int status = -1;
					try {
						status = (int) field.get(vacationfilter);
					} catch (IllegalAccessException e) {
						// throw new IllegalStateException("Error while reading field value: " + field.getName());
						continue;
					}
					if(status != -1) wherePart = String.format(" AND STATUS = %d", status);
					break;
				case "startDate":
					String startDate = null;
					try {
						startDate = (String) field.get(vacationfilter);
					} catch (IllegalAccessException e) {
						// throw new IllegalStateException("Error while reading field value: " + field.getName());
						continue;
					}
					if(startDate != null) wherePart = String.format(" AND START_DATE >= %s", startDate);
					break;
				case "endDate":
					String endDate = null;
					try {
						endDate = (String) field.get(vacationfilter);
					} catch (IllegalAccessException e) {
						// throw new IllegalStateException("Error while reading field value: " + field.getName());
						continue;
					}
					if(endDate != null) wherePart = String.format(" AND END_DATE <= %s", endDate);
					break;
			}
			query.append(wherePart);
		}
		// execute query.
		Query q = entityManager.createNativeQuery(query.toString());
		List<Object[]> resultList = q.getResultList();
		List<VacationDto> vacationDtoList = buildVacationDtoList(resultList);
		return vacationDtoList;
	}

	/**
	 * @param resultList
	 * @return
	 */
	private List<VacationDto> buildVacationDtoList(List<Object[]> resultList) {
		/**
		 * TODO fix the error with constructor.
		 */
		return resultList.stream().map(objectArray -> {
			Long vacationId = ((BigInteger) objectArray[0]).longValue();
			String endDate = (String) objectArray[1];
			String startDate = (String) objectArray[2];
			int status = (Integer) objectArray[3];
			String title = (String) objectArray[4];
			Long userId = (Long) objectArray[5];
			return new VacationDto(vacationId, title, startDate, endDate, status, userId);
		}).collect(toList());
	}

}
