package org.vacation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vacation.beans.UserDto;
import org.vacation.models.User;
import org.vacation.services.impl.UserServiceImpl;

@Service
public class JwtUserService {

	@Autowired
	private UserServiceImpl userService;

	public UserDto loadUserByUsername(String username) {
		UserDto userDto = new UserDto();
		userDto.setUsername(username);
		return userService.create(userDto);
	}
	
}
