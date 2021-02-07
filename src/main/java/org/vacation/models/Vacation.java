package org.vacation.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

	@Column(name="START_DATE", nullable = false)
	private Date startDate;

	@Column(name = "END_DATE", nullable = false)
	private Date endDate;

	@Column(name = "STATUS")
	private int status;

	@ManyToOne(optional = false)
	private User user;
}
