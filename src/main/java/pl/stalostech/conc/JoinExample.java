package pl.stalostech.conc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JoinExample {

	static class A implements Runnable {
		public void run() {
			System.out.printf("Beginning data sources loading: %s\n",
					new Date());
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("Data sources loading has finished: %s\n",
					new Date());
		}
	}

	static class B implements Runnable {
		public void run() {
			System.out.printf("Beginning data sources loading: %s\n",
					new Date());
			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("Data sources loading has finished: %s\n",
					new Date());
		}
	}

	public static void main(String... args) {
		Thread t1 = new Thread(new JoinExample.A());
		Thread t2 = new Thread(new JoinExample.B());
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Init completed.");
	}

}
