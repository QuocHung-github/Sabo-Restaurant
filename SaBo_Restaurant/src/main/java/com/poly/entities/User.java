package com.poly.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`User`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4125343598769226984L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	@NotNull
	private String firstName;
	@Column
	@NotNull
	private String lastName;
	@Column
	@Range(min = 0, max = 2)
	private int gender;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@Column
	@NotNull
	private LocalDate birthday;
	@Column(unique = true)
	@Email
	private String email;
	@Column(unique = true)
	@NotNull
	private String phone;
	@Column
	@NotNull
	private String address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"user"}, allowSetters = true)
	private UserAccount account;
}
