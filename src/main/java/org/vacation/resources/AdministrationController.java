package org.vacation.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vacation.beans.UserDto;
import org.vacation.filters.UserFilter;
import org.vacation.models.User;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/administration")
public class AdministrationController {

	// manage user - add user - delete user, provide access remove access change..
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(@RequestBody UserDto userDto) {
		
	}
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public List<User> getUsers() {
		return Arrays.asList(new User(1l, "userTest"));
	}
	
	@RequestMapping(value = "/remove/{userId}")
	public void removeUser(@RequestParam String userId) {
		
	}
	
	@RequestMapping(value = "/search/{userId}")
	public void searchUser(@RequestParam String userId) {
		
	}
	
	@RequestMapping(value = "/delete/{userId}")
	public void deleteUser(@RequestParam String userId) {
		
	}
	
	@RequestMapping(value = "/modifyUser")
	public void modifyUser(@RequestBody UserDto newUser) {
		
	}
	
}
