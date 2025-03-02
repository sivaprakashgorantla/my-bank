package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmplyeeListSort {
	public static void main(String[] args) {
		 List<Employee> employees = Arrays.asList(
		            new Employee(1, "Alice","MALE", 50000),
		            new Employee(2, "Bob", "FEMALE",60000),
		            new Employee(3, "Charlie","MALE", 45000)
		        );
		employees.stream()
		.sorted((e1,e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
		.forEach(System.out::println);
	}
}











//List<Employee> list = Arrays.asList(
//		new Employee(1, "ABC ","MANE"),
//		new Employee(2, "ABC2 ","FEMANE"),
//		new Employee(3, "ABC3 ","MANE"),
//		new Employee(4, "ABC4 ","FeMANE"),
//		new Employee(5, "ABC5 ","MANE"),
//		new Employee(6, "ABC6 ","FEMANE")
//	);
