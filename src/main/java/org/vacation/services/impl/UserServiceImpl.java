package org.vacation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vacation.beans.UserDto;
import org.vacation.models.User;
import org.vacation.repositories.IUserRepository;
import org.vacation.services.IUserService;

@Service
public class UserServiceImpl extends UserCRUDServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void test() { }


    public User findByUsername(String username) {
        /**
         * TODO chnage to DTO return type.
         */
        return userRepository.findByUsername(username);
    }
}
