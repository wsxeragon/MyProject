package cn.inphase.control;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.inphase.domain.Customer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestFRP {

	@Test
	public void test() {

		// 被观测者
		Observable.create(new ObservableOnSubscribe<String>() {

			// 触发事件
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				System.out.println("事件发生在 " + Thread.currentThread().getName());
				emitter.onNext("触发一下");
				emitter.onNext("再触发一下");
				emitter.onComplete();
			}

		})// .subscribeOn(Schedulers.newThread()) // 指定事件发生的线程
				.observeOn(Schedulers.newThread())// 指定观测到事件后 回调发生的线程
				.subscribe(new Observer<String>() {// 绑定观测者
					@Override
					public void onComplete() {
						System.out.println("onComplete");

					}

					@Override
					public void onError(Throwable arg0) {
						System.out.println("onError");

					}

					@Override
					public void onNext(String arg0) {
						System.out.println("onNext-->" + arg0);
						System.out.println("观测回调发生在 " + Thread.currentThread().getName());

					}

					@Override
					public void onSubscribe(Disposable arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Test
	public void test1() {
		MyObserver myObserver = new MyObserver();
		// 更为简便的创建被观测者
		// 2.0不再支持null值，传入null值会抛出 NullPointerException
		Observable.just("123", "", "哈哈").subscribe(myObserver);

		String[] strs = new String[] { "123", "", "哈哈" };
		Observable.fromArray(strs).subscribe(myObserver);
	}

	@Test
	public void test2() {

		EmailObserver emailObserver = new EmailObserver();
		CoinObserver coinObserver = new CoinObserver();
		// 更为简便的创建被观测者
		// 2.0不再支持null值，传入null值会抛出 NullPointerException
		// Observable.just(new Customer("peter",
		// "17")).subscribe(emailObserver);

		List<Customer> list = new ArrayList<>();
		list.add(new Customer("linda", "25"));
		list.add(new Customer("tom", "19"));
		// Observable.fromIterable(list).subscribe(emailObserver);
		// Observable.fromIterable(list).subscribe(coinObserver);

		MyObservableOnSubscribe<Customer> myObservableOnSubscribe = new MyObservableOnSubscribe();
		Observable<Customer> observable = Observable.create(myObservableOnSubscribe);
		observable.subscribeOn(Schedulers.newThread())// 订阅线程 理解为回调执行的线程
				.observeOn(Schedulers.newThread())// 发送线程 理解为事件发布的线程
				.subscribe(emailObserver);
		// 先关联了订阅预备订阅的关系，后发布事件
		myObservableOnSubscribe.setCustomers(list);
	}
}

class MyObserver implements Observer<String> {
	@Override
	public void onSubscribe(Disposable d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNext(String t) {
		System.out.println("观测到  " + t);
	}

	@Override
	public void onError(Throwable e) {
		System.out.println(e);
	}

	@Override
	public void onComplete() {
		System.out.println("观测结束");

	}
}

class EmailObserver implements Observer<Customer> {
	Customer customer = new Customer();

	@Override
	public void onSubscribe(Disposable d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNext(Customer t) {
		System.out.println(t.getName() + " 注册成功，发送邮件");
		customer = t;
	}

	@Override
	public void onError(Throwable e) {
		System.out.println("error");
	}

	@Override
	public void onComplete() {

	}

}

class CoinObserver implements Observer<Customer> {
	Customer customer = new Customer();

	@Override
	public void onSubscribe(Disposable d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNext(Customer t) {
		System.out.println(t.getName() + " 注册成功，创建积分管理账号");
		customer = t;
	}

	@Override
	public void onError(Throwable e) {

	}

	@Override
	public void onComplete() {

	}

}

class MyObservableOnSubscribe<T> implements ObservableOnSubscribe<T> {

	private List<T> list;

	public void setCustomers(List<T> list) {
		this.list = list;
	}

	@Override
	public void subscribe(ObservableEmitter<T> e) throws Exception {
		for (T c : list) {
			e.onNext(c);
		}
	}

}
