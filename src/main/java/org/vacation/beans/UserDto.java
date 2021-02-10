package org.vacation.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

	private Long userId;

	private String username;

	private List<RoleDto> roleDtoList;

	public UserDto(Long userId, String username) {
		this.userId = userId;
		this.username = username;
		this.roleDtoList = roleDtoList;
	}

	public UserDto(String username, List<RoleDto> roleDtoList) {
		this.username = username;
		this.roleDtoList = roleDtoList;
	}
}
