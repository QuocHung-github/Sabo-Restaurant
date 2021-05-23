package com.poly.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicUpdate
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4644550546794849995L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;
	@Column
	private double price;

	@OneToOne(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH })
	@JoinColumn(name = "restaurantId")
	@JsonIgnoreProperties(value = {"menu"}, allowSetters = true)
	private Restaurant restaurant;

	@OneToOne(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH })
	@JoinColumn(name = "typeId")
	private FoodType type;
}
