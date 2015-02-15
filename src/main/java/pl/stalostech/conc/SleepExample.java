package pl.stalostech.conc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SleepExample implements Runnable {

	public static void main(String... args) {
		Thread t = new Thread(new SleepExample());
		t.start();

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.interrupt();

	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.printf("%s\n", new Date());
			try {
				TimeUnit.MINUTES.sleep(1);
				/*
				 * When Thread is sleeping and is interrupted, the method throws
				 * an InterruptedException exception immediately and doesn't
				 * wait until the sleeping time finishes.The Java concurrency
				 * API has another method that makes a Thread object leave the
				 * CPU. It's the yield() method, which indicates to the JVM that
				 * the Thread object can leave the CPU for other tasks. The JVM
				 * does not guarantee that it will comply with this request.
				 * Normally, it's only used for debug purposes.
				 */
			} catch (InterruptedException e) {
				System.out.printf("The FileClock has been interrupted");
				return;
			}
		}
	}

}
