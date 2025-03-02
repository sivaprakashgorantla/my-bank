package com.example.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArraySplit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		stringOperations();
		
	}

	private static void stringOperations() {
		// TODO Auto-generated method stub
		stringArraySplit();
		nonRepeatedChar();
		arryOperations();
		charFrequency();
	}

	private static void charFrequency() {
		// TODO Auto-generated method stub
		String str = "hello";
		str.chars()
		.mapToObj(ch -> (char) ch)
		.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
		.forEach((key,value) -> System.out.println(key +" : "+value));
	}

	private static void arryOperations() {
		// TODO Auto-generated method stub
		int[] numbers = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
		Arrays.stream(numbers)
		.boxed()
		.sorted(Comparator.reverseOrder())
		.skip(1)
		.findFirst()
		.ifPresent(System.out::println);
		
	}

	private static void nonRepeatedChar() {
		// TODO Auto-generated method stub
		String input ="SWISS";
		input.chars()
		.mapToObj( ch -> (char) ch)
		.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
		.entrySet()
		.stream()
		.filter(entry -> entry.getValue() ==1)
		.findFirst()
		.ifPresentOrElse( entry -> 
				System.out.println(entry.getKey() +":"+entry.getValue())
				, () -> new Exception("Not found any char"));
	}

	private static void stringArraySplit() {
		// TODO Auto-generated method stub
		String input = "apple, banana, apricot, cherry, avocado";
		Arrays.stream(input.split(","))
		.map(String::trim)
		.map(String::toUpperCase)
		.filter(s -> s.startsWith("A"))
		 .collect(Collectors.toList())
		 .forEach(System.out::println);

	}

}
