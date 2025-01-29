package com.example.demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test Demo");
		
		List<Employee> list = Arrays.asList(
				new Employee(10,"Sivaprakash "),
				new Employee(20,"xyzer"),
				new Employee(30,"pqriu"),
				new Employee(40,"ACB"),
				new Employee(50,"mnl"),
				new Employee(60,"ACB")
				);
		
		list.stream().sorted(Comparator.comparing(Employee::getName))
		.skip(2).forEach(emp -> System.out.println(emp));
		/*
		list.stream()
		.collect(Collectors.groupingBy(Employee::getName.lentch),Collectors.counting())
		.forEach((Integer,Integer) -> System.out.println(""));
		*/
	}

}
