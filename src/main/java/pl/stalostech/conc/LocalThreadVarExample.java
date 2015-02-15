package pl.stalostech.conc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LocalThreadVarExample implements Runnable {

	/**
	 * Thread-local variables store a value of an attribute for each Thread that
	 * uses one of these variables. You can read the value using the get()
	 * method and change the value using the set() method. The first time you
	 * access the value of a thread-local variable, if it has no value for the
	 * Thread object that it is calling, the thread-local variable calls the
	 * initialValue() method to assign a value for that Thread and returns the
	 * initial value.
	 */
	private static ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
		protected Date initialValue() {
			return new Date();
		}
	};

	/**
	 * The Java Concurrency API includes the InheritableThreadLocal class that
	 * provides inheritance of values for threads created from a thread. If a
	 * thread A has a value in a thread- local variable and it creates another
	 * thread B, the thread B will have the same value as the thread A in the
	 * thread-local variable. You can override the childValue() method that is
	 * called to initialize the value of the child thread in the thread-local
	 * variable. It receives the value of the parent thread in the thread-local
	 * variable as a parameter.
	 */

	public static void main(String... args) {
		LocalThreadVarExample task = new LocalThreadVarExample();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(task);
			thread.start();
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread()
				.getId(), startDate.get());
		try {
			TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Thread Finished: %s : %s\n", Thread.currentThread()
				.getId(), startDate.get());
	}

}
