package com.capgemini.seetbooking.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "offices")
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "office_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String location;

	@OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
	private List<Floor> floors;

	// Add other office details like floors, seating capacity, etc.

	// Constructors, getters, setters

	public List<Floor> getFloors() {
		return floors;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public Office() {
	}

	public Office(Long id, String name, String location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Office [id=" + id + ", name=" + name + ", location=" + location + "]";
	}

}
