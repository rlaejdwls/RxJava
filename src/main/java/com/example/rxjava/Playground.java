package com.example.rxjava;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class Playground {
	public static class MultiplicationTable extends Basic {
		public class Data {
			private int left;
			private int right;
			private int result;
		}
		
		@Override
		public void run() {
			Integer[] array = new Integer[] { 2, 3, 4, 5, 6, 7, 8, 9 };
			
			Observable.fromArray(array)
			.flatMap(left -> {
				Integer[] dan = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
				System.out.println("------------" + left + "´Ü ------------");
				
				return Observable.fromArray(dan)
				.map(right -> {
					Data result = new Data();
					result.left = left;
					result.right = right;
					result.result = left * right;
					return result;
				});
			})
			.subscribe(result -> {
				System.out.println(result.left + " * " + result.right + " = " + result.result);
			});
		}
	}
	
	public static void main(String[] args) {
		List<Basic> list = new ArrayList<>();
		list.add(new MultiplicationTable());
		
		for (Basic parent : list) {
			parent.run();
		}
	}
}
