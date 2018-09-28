package com.example.rxjava;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RxJavaBasic {
	public static class ObservableBasic extends Basic {
		public void just() {
			print("just");
			Disposable disposable = Observable.just("Hello", "Rx World")
			.subscribe(
					(data) -> System.out.println(data),
					(error) -> System.out.println("onError"),
					() -> System.out.println("onComplete"));
			System.out.println(disposable.isDisposed());
		}
		public void create() {
			print("create");
			Observable.create((emitter) -> {
				emitter.onNext("Hello");
				emitter.onNext("Rx World");
				emitter.onComplete();
			}).subscribe(System.out::println);
		}
		public <T> void fromArray(T[] data) {
			print("fromArray");
			Observable.fromArray(data)
			.subscribe(System.out::println);
		}
		public <T> void fromIterable(Iterable<T> datas) {
			print("fromIterable");
			Observable.fromIterable(datas)
			.subscribe(System.out::println);
		}
		public <T> void fromCallable(Callable<T> callable) {
			print("fromCallable");
			Observable.fromCallable(callable)
			.subscribe(System.out::println);
		}
		public <T> void fromFuture(Callable<T> callable) {
			print("fromFuture");
			Observable.fromFuture(Executors.newSingleThreadExecutor().submit(callable))
			.subscribe(System.out::println);
		}
		public <T> void fromPublisher(Publisher<T> publisher) {
			print("fromPublisher");
			Observable.fromPublisher(publisher)
			.subscribe(System.out::println);
		}
	}
	public static class SingleBasic extends Basic {
		public void just() {
			Single.just("Hello Single")
			.subscribe(System.out::println);
		}
		public <T> void fromObservable(Observable<T> source) {
			Single.fromObservable(source)
			.subscribe(System.out::println);
		}
		public void single() {
			Observable.just("Just Item")
			.single("Default Item")
			.subscribe(System.out::println);
		}
		public void first() {
			Observable.fromArray(new String[] { "Array1", "Array2", "Array3" })
			.first("Default Array0")
			.subscribe(System.out::println);
		}
		public void take() {
			Observable.fromArray(new String[] { "Array1", "Array2", "Array3" })
			.take(1)
			.single("Default Array0")
			.subscribe(System.out::println);
		}
	}
	public static class AsyncSubjectBasic extends Basic {
		public void example1() {
			print("example1");
			AsyncSubject<String> subject = AsyncSubject.create();
			subject.subscribe(data -> System.out.println("AsyncSubscriber #1 => " + data));
			subject.onNext("1");
			subject.onNext("2");
			subject.subscribe(data -> System.out.println("AsyncSubscriber #2 => " + data));
			subject.onNext("3");
			subject.onComplete();
		}
		public void example2() {
			print("example2");
			Float[] temperature = { 10.1f, 13.4f, 12.5f };
			AsyncSubject<Float> subject = AsyncSubject.create();
			subject.subscribe(data -> System.out.println("AsyncSubscriber #1 => " + data));
			
			Observable.fromArray(temperature)
			.subscribe(subject);
		}
		public void example3() {
			print("example3");
			AsyncSubject<String> subject = AsyncSubject.create();
			subject.onNext("1");
			subject.subscribe(data -> System.out.println("AsyncSubscriber #1 => " + data));
			subject.onNext("2");
			subject.onComplete();
			subject.onNext("3");
			subject.subscribe(data -> System.out.println("AsyncSubscriber #2 => " + data));
			subject.subscribe(data -> System.out.println("AsyncSubscriber #3 => " + data));
		}
	}
	public static class BehaviorSubjectBasic extends Basic {
		public void example1() {
			print("example1");
			BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
			subject.subscribe(data -> System.out.println("BehaviorSubscriber #1 => " + data));
			subject.onNext("1");
			subject.onNext("2");
			subject.subscribe(data -> System.out.println("BehaviorSubscriber #2 => " + data));
			subject.onNext("3");
			subject.onComplete();
		}
	}
	public static class PublishSubjectBasic extends Basic {
		public void example1() {
			print("example1");
			PublishSubject<String> subject = PublishSubject.create();
			subject.subscribe(data -> System.out.println("PublishSubscriber #1 => " + data));
			subject.onNext("1");
			subject.onNext("2");
			subject.subscribe(data -> System.out.println("PublishSubscriber #2 => " + data));
			subject.onNext("3");
			subject.onComplete();
		}
	}
	public static class ReplaySubjectBasic extends Basic {
		public void example1() {
			print("example1");
			ReplaySubject<String> subject = ReplaySubject.create();
			subject.subscribe(data -> System.out.println("ReplaySubscriber #1 => " + data));
			subject.onNext("1");
			subject.onNext("2");
			subject.onNext("3");
			subject.subscribe(data -> System.out.println("ReplaySubscriber #2 => " + data));
			subject.onNext("4");
			subject.onComplete();
		}
	}
	public static class ConnectableObservableBasic extends Basic {
		public void example1(String[] array) throws Exception {
			print("example1");
			
			ConnectableObservable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
			.map(Long::intValue)
			.map(i -> array[i])
			.take(array.length)
			.publish();
			
			source.subscribe(data -> System.out.println("ReplaySubscriber #1 => " + data));
			source.subscribe(data -> System.out.println("ReplaySubscriber #2 => " + data));
			source.connect();
			
			Thread.sleep(250);
			source.subscribe(data -> System.out.println("ReplaySubscriber #3 => " + data));
			Thread.sleep(100);
		}
	}
	public static class OperatorsBasic extends Basic {
		public void map(String data) {
			print("map");
			Observable.just(data)
			.map(Integer::parseInt)
			.subscribe(System.out::println);
		}
		public void map(String[] array) {
			print("map");
			Observable.fromArray(array)
			.map(s -> {
				switch(s) {
				case "100":
					return 1;
				case "101":
					return 2;
				default:
					return 3;
				}
			})
			.subscribe(System.out::println);
		}
		public void flatMap(String[] array) {
			print("flatMap");
			Observable.fromArray(array)
			.flatMap(s -> Observable.just(s + "1", s + "2"))
			.subscribe(System.out::println);
		}
		public void filter(String[] array) {
			print("filter");
			Observable.fromArray(array)
			.filter(s -> !(s.indexOf("CIRCLE") > -1))
			.subscribe(System.out::println);
		}
		public void first(String[] array) {
			print("first");
			Observable.fromArray(array)
			.first("0 DEFAULT")
			.subscribe(System.out::println);
		}
		public void last(String[] array) {
			print("last");
			Observable.fromArray(array)
			.last("9 MAXIMUM")
			.subscribe(System.out::println);
		}
		public void take(int count, String[] array) {
			print("take");
			Observable.fromArray(array)
			.take(count)
			.subscribe(System.out::println);
		}
		public void takeLast(int count, String[] array) {
			print("takeLast");
			Observable.fromArray(array)
			.takeLast(count)
			.subscribe(System.out::println);
		}
		public void skip(int count, String[] array) {
			print("skip");
			Observable.fromArray(array)
			.skip(count)
			.subscribe(System.out::println);
		}
		public void skipLast(int count, String[] array) {
			print("skipLast");
			Observable.fromArray(array)
			.skipLast(count)
			.subscribe(System.out::println);
		}
		public void reduce(String[] array) {
			print("reduce");
			Observable.fromArray(array)
			.reduce((in, out) -> out + "(" + in + ")")
			.subscribe(System.out::println);
		}
	}
	
	public static void main(String[] args) throws Exception {
		RxJavaBasic.ObservableBasic observer = new ObservableBasic();
		observer.just();
		observer.create();
		observer.fromArray(new Integer[] { 100, 200, 300 } );
		observer.fromIterable(Arrays.asList(new String[] { "Very", "Good" }));
		
		Map<String, String> map = new HashMap<>();
		map.keySet();
		map.put("key1", "value1");
		map.put("key2", "value2");
		observer.fromIterable(map.keySet());
		observer.fromCallable(() -> {
			return "Hello Rx World";
		});
		observer.fromFuture(() -> {
			return "Hello Future";
		});
		observer.fromPublisher(subscriber -> {
			subscriber.onNext("Hello Observable.fromPublisher");
			subscriber.onComplete();
		});
		
		RxJavaBasic.SingleBasic single = new SingleBasic();
		single.just();
		single.fromObservable(Observable.just("Single.fromObservable"));
		single.single();
		single.first();
		single.take();
		
		RxJavaBasic.AsyncSubjectBasic asyncSubject = new AsyncSubjectBasic();
		asyncSubject.example1();
		asyncSubject.example2();
		asyncSubject.example3();
		
		RxJavaBasic.BehaviorSubjectBasic behaviorSubject = new BehaviorSubjectBasic();
		behaviorSubject.example1();
		
		RxJavaBasic.PublishSubjectBasic publishSubject = new PublishSubjectBasic();
		publishSubject.example1();
		
		RxJavaBasic.ReplaySubjectBasic replaySubject = new ReplaySubjectBasic();
		replaySubject.example1();
		
		RxJavaBasic.ConnectableObservableBasic connectableObservable = new ConnectableObservableBasic();
		connectableObservable.example1(new String[] { "1", "2", "3" });
		
		RxJavaBasic.OperatorsBasic operators = new OperatorsBasic();
		operators.map("10");
		operators.map(new String[] { "100", "101", "102", "103" });
		operators.flatMap(new String[] { "100", "101", "102", "103" });
		operators.filter(new String[] { "1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON" });
		operators.first(new String[] { "1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON" });
		operators.last(new String[] { "1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON" });
		operators.take(3, new String[] { "1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON" });
		operators.takeLast(2, new String[] { "1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON" });
		operators.skip(1, new String[] { "1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON" });
		operators.skipLast(4, new String[] { "1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON" });
		operators.reduce(new String[] { "1", "3", "5" });
	}
}
