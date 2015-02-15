package pl.stalostech.conc;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * An interesting functionality offered by the concurrency API of Java is the
 * ability to group the threads. This allows us to treat the threads of a group
 * as a single unit and provides access to the Thread objects that belong to a
 * group to do an operation with them. For example, you have some threads doing
 * the same task and you want to control them, irrespective of how many threads
 * are still running, the status of each one will interrupt all of them with a
 * single call.
 *
 */
public class ThreadGroupExample implements Runnable {

	static class Result {
		public String name;
	}

	private Result result;

	public static void main(String... args) {
		ThreadGroup threadGroup = new ThreadGroup("Searcher");

		Result result = new Result();
		ThreadGroupExample t = new ThreadGroupExample(result);

		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(threadGroup, t);
			thread.start();

			System.out.printf("Number of Threads: %d\n",
					threadGroup.activeCount());
			System.out.printf("Information about the Thread Group\n");
			threadGroup.list();
			//threadGroup.interrupt();
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public ThreadGroupExample(ThreadGroupExample.Result result) {
		this.result = result;
	}

	public void run() {
		String name = Thread.currentThread().getName();
		System.out.printf("Thread %s: Start\n", name);
		try {
			doTask();
			result.name = name;
		} catch (InterruptedException e) {
			System.out.printf("Thread %s: Interrupted\n", name);
			return;
		}
		System.out.printf("Thread %s: End\n", name);
	}

	private void doTask() throws InterruptedException {
		Random random = new Random((new Date()).getTime());
		int value = (int) (random.nextDouble() * 100);
		System.out.printf("Thread %s: %d\n", Thread.currentThread().getName(),
				value);
		TimeUnit.SECONDS.sleep(value);
	}

}
