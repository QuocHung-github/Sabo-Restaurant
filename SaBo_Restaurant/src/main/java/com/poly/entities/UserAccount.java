package com.poly.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 726445773684927784L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	@NotNull
	private String username;

	@Column
	@NotNull
	private String password;

	@Column
	private boolean active;

	@OneToOne
	@JoinColumn(name = "userId")
	@JsonIgnoreProperties(value = {"account"}, allowSetters = true)
	private User user;

	@OneToOne
	@JoinColumn(name = "restaurantId")
	@JsonIgnoreProperties(value = {"manager"}, allowSetters = true)
	private Restaurant restaurant;
	
	@OneToOne
	@JoinColumn(name = "roleId")
	private Roles role;
}
