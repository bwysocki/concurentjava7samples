package pl.stalostech.conc.criticalsection;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * For these types of situations, Java provides the wait() , notify() , and
 * notifyAll() methods implemented in the Object class. A thread can call the
 * wait() method inside a synchronized block of code. If it calls the wait()
 * method outside a synchronized block of code, the JVM throws an
 * IllegalMonitorStateException exception. When the thread calls the wait()
 * method, the JVM puts the thread to sleep and releases the object that
 * controls the synchronized block of code that it's executing and allows the
 * other threads to execute other blocks of synchronized code protected by that
 * object. To wake up the thread, you must call the notify() or notifyAll()
 * method inside a block of code protected by the same object.
 */
public class WaitNotifyExample {

	public static class EventStorage {
		public int maxSize;
		public List<Date> storage;

		public EventStorage() {
			maxSize = 10;
			storage = new LinkedList<>();
		}

		public synchronized void set() {
			while (storage.size() == maxSize) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			storage.add(new Date());
			System.out.printf("Set: %d", storage.size());
			notifyAll();
		}

		public synchronized void get() {
			while (storage.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.printf("Get: %d: %s", storage.size(),
					((LinkedList<?>) storage).poll());
			notifyAll();
		}
	}

	public static class Producer implements Runnable {

		private EventStorage storage;

		public Producer(EventStorage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				storage.set();
			}
		}

	}

	public static class Consumer implements Runnable {

		private EventStorage storage;

		public Consumer(EventStorage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				storage.get();
			}
		}

	}

	public static void main(String... args) {
		EventStorage storage = new EventStorage();
		Producer producer = new Producer(storage);
		Thread thread1 = new Thread(producer);
		Consumer consumer = new Consumer(storage);
		Thread thread2 = new Thread(consumer);
		thread2.start();
		thread1.start();
	}

}
