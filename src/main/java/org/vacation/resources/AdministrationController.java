package org.vacation.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vacation.beans.UserDto;
import org.vacation.filters.UserFilter;

@RestController
@RequestMapping("/admin")
public class AdministrationController {

	// manage user - add user - delete user, provide access remove access change..
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(@RequestBody UserDto userDto) {
		
	}
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public void getUsers(@RequestBody UserFilter userFilter) {
		
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
