package pl.stalostech.conc;

public class HelloWorld implements Runnable {

	private int nr;

	public static void main(String... args) {
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new HelloWorld(i));
			thread.start(); // creates new execution thread
		}
	}

	public HelloWorld(int nr) {
		this.nr = nr;
	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out
					.printf("Thread name : %s, thread number : %d, thread iteration : %d \n",
							Thread.currentThread().getName(), nr, i);
			/**
			 * ID: This attribute stores a unique identifier for each Thread .
			 * Name: This attribute store the name of Thread . Priority: This
			 * attribute stores the priority of the Thread objects. Threads can
			 * have a priority between one and 10, where one is the lowest
			 * priority and 10 is the highest one. It's not recommended to
			 * change the priority of the threads, but it's a possibility that
			 * you can use if you want. Status: This attribute stores the
			 * status of Thread . In Java, Thread can be in one of these six
			 */
		}
	}

}
