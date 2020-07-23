package com.young.other.sql.bean;

import lombok.Data;

import java.util.Objects;

@Data
public class GeologicalDisastersVillage{
	private String id;
	private String street;
	private String name;
	private String head;
	private String phone;
	private String town;
	private String county;
	private String city;

	public GeologicalDisastersVillage(String id, String street, String name, String head, String phone, String town, String county, String city) {
		this.id = id;
		this.street = street;
		this.name = name;
		this.head = head;
		this.phone = phone;
		this.town = town;
		this.county = county;
		this.city = city;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GeologicalDisastersVillage that = (GeologicalDisastersVillage) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}