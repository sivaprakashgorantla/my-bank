package com.example.demo;

public class Employee {
	private int eno;
	private String name;
	private String gender;

	public Employee(int eno, String name) {
		super();
		this.eno = eno;
		this.name = name;
	}
	
	

	public Employee(int eno, String name, String gender) {
		super();
		this.eno = eno;
		this.name = name;
		this.gender = gender;
	}



	public int getEno() {
		return eno;
	}

	public void setEno(int eno) {
		this.eno = eno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [eno=" + eno + ", name=" + name + "]";
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}

}
