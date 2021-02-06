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

@Component
public class VacationTransformerImpl extends Transformer<VacationDto, Vacation> {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private Transformer<UserDto, User> userTransformer;

    @Override
    public VacationDto toDto(Vacation vacation) {
        VacationDto vacationDto = new VacationDto();
        vacationDto.setVacationId(vacation.getId());
        vacationDto.setVacationTitle(vacation.getTitle());
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
