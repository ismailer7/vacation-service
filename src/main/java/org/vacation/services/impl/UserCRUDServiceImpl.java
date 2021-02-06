package org.vacation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.vacation.beans.UserDto;
import org.vacation.models.User;
import org.vacation.repositories.IUserRepository;
import org.vacation.services.ICRUDService;
import org.vacation.transformers.Transformer;

public class UserCRUDServiceImpl implements ICRUDService<UserDto, Long> {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private Transformer<UserDto, User> userTransformer;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if(user != null) {
            // user already exist/
        } else {
            // user not exist.
            User userToBeSaved = userTransformer.toEntity(userDto);
            user = userRepository.save(userToBeSaved);
        }
        return user != null ? userTransformer.toDto(user) : null;
    }

    @Override
    public UserDto getById(Long userId) {
        User user = userRepository.getOne(userId);
        return userTransformer.toDto(user);
    }

    @Override
    public UserDto update(Long aLong, UserDto userDto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
        return;
    }
}
