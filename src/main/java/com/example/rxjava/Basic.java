package com.example.rxjava;

public class Basic {
	public static final int DIVIDE_SIZE = 40;
	
	public Basic() {
		String title = this.getClass().getSimpleName();
		print(title);
	}
	public void print(String data) {
		String result = "";
		int count = DIVIDE_SIZE - data.length();
		for (int i = 0; i < count; i++) { result += "-"; }
		System.out.println(result + data);
	}
	
	public void run() {};
}
