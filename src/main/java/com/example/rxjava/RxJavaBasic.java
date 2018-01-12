package com.example.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RxJavaBasic {
	public void just() {
		Disposable disposable = Observable.just("Hello", "Rx World")
		.subscribe(
				(data) -> System.out.println(data),
				(error) -> System.out.println("onError"),
				() -> System.out.println("onComplete"));
		System.out.println(disposable.isDisposed());
	}
	public void create() {
		Observable.create((emitter) -> {
			emitter.onNext("Hello");
			emitter.onNext("Rx World");
			emitter.onComplete();
		}).subscribe(System.out::println);
	}
	public static void main(String[] args) {
		new RxJavaBasic().just();
		new RxJavaBasic().create();
	}
}
