package org.vacation.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.vacation.beans.UserDto;
import org.vacation.beans.VacationDto;
import org.vacation.models.User;
import org.vacation.models.Vacation;
import org.vacation.repositories.IUserRepository;
import org.vacation.services.IUserService;
import org.vacation.services.impl.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class VacationTransformerImpl extends Transformer<VacationDto, Vacation> {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private Transformer<UserDto, User> userTransformer;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");

    @Override
    public VacationDto toDto(Vacation vacation) {
        VacationDto vacationDto = new VacationDto();
        vacationDto.setVacationId(vacation.getId());
        vacationDto.setVacationTitle(vacation.getTitle());
        Date startDate = vacation.getStartDate();
        if(startDate != null) {
            String startDateString = dateFormat.format(startDate);
            vacationDto.setStartDate(startDateString);
        }
        Date endDate = vacation.getEndDate();
        if(endDate != null) {
            String endDateString = dateFormat.format(endDate);
            vacationDto.setEndDate(endDateString);
        }

        vacationDto.setUserId(vacation.getUser().getId());
        return vacationDto;
    }

    @Override
    public Vacation toEntity(VacationDto vacationDto) {
        Vacation vacation = new Vacation();
        Long userId = vacationDto.getUserId();
        UserDto userDto = userService.getById(userId);
        User user = userTransformer.toEntity(userDto);
        vacation.setUser(user);
        vacation.setTitle(vacationDto.getVacationTitle());
        return vacation;
    }
}
