package com.example.rxjava;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.reactivestreams.Publisher;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RxJavaBasic {
	public static class ObservableBasic {
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
		public <T> void fromArray(T[] data) {
			Observable.fromArray(data)
			.subscribe(System.out::println);
		}
		public <T> void fromIterable(Iterable<T> datas) {
			Observable.fromIterable(datas)
			.subscribe(System.out::println);
		}
		public <T> void fromCallable(Callable<T> callable) {
			Observable.fromCallable(callable)
			.subscribe(System.out::println);
		}
		public <T> void fromFuture(Callable<T> callable) {
			Observable.fromFuture(Executors.newSingleThreadExecutor().submit(callable))
			.subscribe(System.out::println);
		}
		public <T> void fromPublisher(Publisher<T> publisher) {
			Observable.fromPublisher(publisher)
			.subscribe(System.out::println);
		}
	}
	
	public static void main(String[] args) {
		RxJavaBasic.ObservableBasic basic = new RxJavaBasic.ObservableBasic();
		
		basic.just();
		basic.create();
		basic.fromArray(new Integer[] { 100, 200, 300 } );
		basic.fromIterable(Arrays.asList(new String[] { "Very", "Good" }));
		
		Map<String, String> map = new HashMap<>();
		map.keySet();
		map.put("key1", "value1");
		map.put("key2", "value2");
		basic.fromIterable(map.keySet());
		basic.fromCallable(() -> {
			return "Hello Rx World";
		});
		basic.fromFuture(() -> {
			return "Hello Future";
		});
		basic.fromPublisher(subscriber -> {
			subscriber.onNext("Hello Observable.fromPublisher");
			subscriber.onComplete();
		});
	}
}
