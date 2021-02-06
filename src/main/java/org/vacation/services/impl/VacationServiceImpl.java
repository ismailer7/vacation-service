package org.vacation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vacation.beans.VacationDto;
import org.vacation.filters.VacationFilter;
import org.vacation.models.User;
import org.vacation.models.Vacation;
import org.vacation.repositories.IUserRepository;
import org.vacation.repositories.IVacationRepository;
import org.vacation.services.IUserService;
import org.vacation.services.IVacationService;
import org.vacation.transformers.Transformer;
import org.vacation.transformers.VacationTransformerImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
		 */
		Class<?> c = vacationfilter.getClass();
		for (Field field: c.getDeclaredFields()) {
			String filedName = field.getName();
			switch(filedName) {
				case "createdBy":
					String createdBy = null;
					try {
						createdBy = (String) field.get(vacationfilter); // is username.
					} catch (IllegalAccessException e) {
						throw new IllegalStateException("Error while reading filed value: " + field.getName());
					}
					User user = userService.findByUsername(createdBy);
					Long createdById = user.getId();
					String wherePart = String.format(" AND USER_ID = %s", createdById);
					query.append(wherePart);
			}
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
		return resultList.stream()
				  .map(objectArray -> new VacationDto((Long) objectArray[0], (String) objectArray[1], (Long) objectArray[3]))
				  .collect(toList());
	}

}
