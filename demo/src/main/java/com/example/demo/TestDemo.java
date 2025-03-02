package com.example.demo;

import java.util.Comparator;
import java.util.TreeSet;

public class TestDemo {
	public static void main(String[] args) {
		TreeSet<Integer> numbers = new TreeSet<>(Comparator.nullsFirst(Integer::compareTo));

        numbers.add(null); // Now allowed
        numbers.add(10);
        numbers.add(20);
        System.out.println(numbers);
	}
}
