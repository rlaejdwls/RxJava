package com.example.rxjava;

import io.reactivex.Observable;

public class RxJavaBasic {
	public void emit() {
		Observable.just("Hello", "Rx World")
		.subscribe(System.out::println);
	}
	public static void main(String[] args) {
		new RxJavaBasic().emit();
	}
}
