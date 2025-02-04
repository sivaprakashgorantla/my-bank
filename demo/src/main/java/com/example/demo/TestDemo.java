package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestDemo {
	public static void main(String[] args) {

		List<Employee> list = Arrays.asList(
					new Employee(1, "ABC ","MANE"),
					new Employee(2, "ABC2 ","FEMANE"),
					new Employee(3, "ABC3 ","MANE"),
					new Employee(4, "ABC4 ","FeMANE"),
					new Employee(5, "ABC5 ","MANE"),
					new Employee(6, "ABC6 ","FEMANE")
				);
		list.stream()
		.collect(Collectors.groupingBy(Employee::getGender))
		
		.forEach((gender,employees) -> {
			
			
		});
	}
}
