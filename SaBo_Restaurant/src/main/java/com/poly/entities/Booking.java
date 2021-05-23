package com.poly.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4309336917566999167L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Column
	@NotNull
	private LocalDateTime createdDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Column
	@NotNull
	private LocalDateTime bookingDate;
	@Column
	@Min(value = 1)
	private int persons;
	@Column
	@Range(min = 0, max = 2)
	private int status;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH })
	@JoinColumn(name = "restaurantId")
	private Restaurant restaurant;

	@OneToOne(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH })
	@JoinColumn(name = "userId")
	private User user;
}
