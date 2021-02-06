package org.vacation.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "VACATION")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vacation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "TITLE", length = 100)
	private String title;

	@ManyToOne(optional = false)
	private User user;
}
