package com.example.rxjava;

public class Basic {
	public static final int DIVIDE_SIZE = 40;
	
	public Basic() {
		String title = this.getClass().getSimpleName();
		int count = DIVIDE_SIZE - title.length();
		for (int i = 0; i < count; i++) { System.out.print("-"); }
		System.out.println(title);
	}
	
	public void run() {};
}
