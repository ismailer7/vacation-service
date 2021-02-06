package org.vacation.transformers;

import org.springframework.stereotype.Component;
import org.vacation.beans.UserDto;
import org.vacation.models.User;

@Component
public class UserTranformerImpl extends Transformer<UserDto, User> {

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        if(userDto.getUserId() != null) user.setId(userDto.getUserId());
        // user.setId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        return user;
    }
}
