package org.vacation.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "USER")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USERNAME", unique = true)
	private String username;

	@OneToMany
	private List<Role> roles;

	public User(Long id, String username) {
		this.id = id;
		this.username = username;
	}
}
