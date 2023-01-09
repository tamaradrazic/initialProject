package com.example.projekat.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

@Entity
@XmlRootElement(name = "patient")
@Table(name = "patient", schema = "project")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "firstName")
	@CsvBindByPosition(position = 0)
	private String firstName;

	@Column(name = "lastName")
	@CsvBindByPosition(position = 1)
	private String lastName;

	@Column(name = "date")
	@CsvBindByPosition(position = 6)
	@CsvDate("yyyy-MM-dd")
	private Date date;

	@Column(name = "age")
	@CsvBindByPosition(position = 2)
	private int age;

	@Column(name = "email")
	@CsvBindByPosition(position = 3)
	private String email;

	@Column(name = "weight")
	@CsvBindByPosition(position = 4)
	private double weight;

	@Column(name = "heigth")
	@CsvBindByPosition(position = 5)
	private double height;

	public Patient() {

	}

	public Patient(String firstName, String lastName, String email, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getWeight() {
		return weight;
	}

	public double getHeight() {
		return height;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", date=" + date + ", age="
				+ age + ", email=" + email + ", weight=" + weight + ", height=" + height + "]";
	}
}
