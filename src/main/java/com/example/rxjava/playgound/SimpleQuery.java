package com.example.rxjava.playgound;

import java.util.ArrayList;
import java.util.List;

import com.example.rxjava.Basic;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SimpleQuery extends Basic {
	public enum Type {
		TV,
		Camera,
		Phone
	}
	public class Data {
		private Type type;
		private int price;
		
		public Data(Type type, int price) {
			super();
			this.type = type;
			this.price = price;
		}
		@Override
		public String toString() {
			return "Data [type=" + type + ", price=" + price + "]";
		}
	}
	private List<Data> list = new ArrayList<>();
	public SimpleQuery() {
		list.add(new Data(Type.TV, 2500));
		list.add(new Data(Type.Camera, 300));
		list.add(new Data(Type.TV, 1600));
		list.add(new Data(Type.Phone, 800));
		list.add(new Data(Type.Phone, 720));
		list.add(new Data(Type.TV, 1200));
		list.add(new Data(Type.Camera, 100));
		list.add(new Data(Type.Phone, 1100));
	}
	
	@Override
	public void run() {
		System.out.println("½ÃÀÛ");
		Observable.fromIterable(list)
		.subscribeOn(Schedulers.newThread())
		.filter(data -> data.type == Type.TV)
		.map(data -> data.price)
		.reduce((sum, price) -> sum + price)
		.observeOn(Schedulers.newThread())
		.subscribe(sum -> {
			System.out.println("TV total sales amount : " + sum);
		});
		System.out.println("³¡?");
	}
	
	public static void main(String[] args) {
		new SimpleQuery().run();
		try { Thread.sleep(1500); } catch (Exception e) {}
	}
}
