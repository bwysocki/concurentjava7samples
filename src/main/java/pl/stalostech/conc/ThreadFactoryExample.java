package pl.stalostech.conc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadFactoryExample implements ThreadFactory {

	public static class Task implements Runnable {
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int counter;
	private String name;
	private List<String> stats;

	public static void main(String[] args) {
		ThreadFactoryExample factory = new ThreadFactoryExample(
				"MyThreadFactory");
		Task task = new Task();
		Thread thread;
		System.out.printf("Starting the Threads\n");
		for (int i = 0; i < 10; i++) {
			thread = factory.newThread(task);
			thread.start();
		}
		System.out.printf("Factory stats:\n");
		System.out.printf("%s\n",factory.getStats());
	}

	public ThreadFactoryExample(String name) {
		counter = 0;
		this.name = name;
		stats = new ArrayList<String>();
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, name + "-Thread_" + counter);
		counter++;
		stats.add(String.format("Created thread %d with name %s on %s\n",
				t.getId(), t.getName(), new Date()));
		return t;
	}

	public String getStats() {
		StringBuffer buffer = new StringBuffer();
		Iterator<String> it = stats.iterator();
		while (it.hasNext()) {
			buffer.append(it.next());
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
