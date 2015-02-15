package pl.stalostech.conc;

import java.lang.Thread.UncaughtExceptionHandler;

/*
 * The Thread class has another method related to the process of uncaught exceptions. It's the
 static method setDefaultUncaughtExceptionHandler() that establishes an exception
 handler for all the Thread objects in the application.
 */
public class UncheckedExceptionExample implements Runnable {

	public static class ExceptionHandler implements UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			System.out.printf("An exception has been captured\n");
			System.out.printf("Thread: %s\n", t.getId());
			System.out.printf("Exception: %s: %s\n", e.getClass().getName(),
					e.getMessage());
			System.out.printf("Stack Trace: \n");
			e.printStackTrace(System.out);
			System.out.printf("Thread status: %s\n", t.getState());
		}
	}

	public static void main(String... args) {
		Thread t = new Thread(new UncheckedExceptionExample());
		t.setUncaughtExceptionHandler(new ExceptionHandler());
		t.start();
	}

	public void run() {
		Integer.parseInt("TTT");
	}

}
