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
			Observable.range(2, 8)
			.flatMap(left -> {
				System.out.println("------------" + left + "´Ü ------------");
				
				return Observable.range(1, 9)
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
