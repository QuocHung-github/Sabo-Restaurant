package com.poly.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6620961854711652343L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	@NotNull
	private String restaurantName;
	@Column
	@Email
	@NotNull
	private String email;
	@Column
	@Size(max = 10, min = 10)
	@NotNull
	private String phone;
	@Column
	@NotNull
	private String address;
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@Column
	@NotNull
	private LocalTime openTime;
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@Column
	@NotNull
	private LocalTime closeTime;
	@Column
	private int state; // 0 là đóng cửa, 1 là mở cửa, 2 là tạm ngưng
	@Column
	private String overview;
	@Column
	private int bookedTimes;

	@OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"restaurant"}, allowSetters = true)
	private UserAccount manager;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "logo")
	private Image logo;

	@OneToMany(mappedBy = "restaurant", cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE,
			CascadeType.DETACH })
	@JsonIgnoreProperties(value = {"restaurant"}, allowSetters = true)
	private List<Food> menu;
}
