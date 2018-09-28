package com.example.rxjava.playgound;

import java.util.Scanner;

import com.example.rxjava.Basic;

import io.reactivex.Observable;

public class MultiplicationTable extends Basic {
	private Scanner scanner;
	@Override
	public void run() {
		scanner = new Scanner(System.in);
		String data;
		
		while(true) {
			System.out.print("Input:");
			data = scanner.nextLine();
			try {
				Integer.parseInt(data);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Input type is only integer");
			}
		}
		
		print("Function");
		Observable.just(Integer.parseInt(data))
		.flatMap(left -> Observable.range(1, 9).map(right -> left + " * " + right + " = " + (left * right)))
		.subscribe(System.out::println);

		print("Function+BiFunction");
		Observable.just(Integer.parseInt(data))
		.flatMap(left -> Observable.range(1, 9), (left, right) -> left + " * " + right + " = " + (left * right))
		.subscribe(System.out::println);
	}
	
	public static void main(String[] args) {
		new MultiplicationTable().run();
	}
}
