package pl.stalostech.conc.criticalsection;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * It allows the structuring of synchronized blocks in a more flexible way. With the
 synchronized keyword, you have to get and free the control over a synchronized
 block of code in a structured way. The Lock interfaces allow you to get more complex
 structures to implement your critical section.
 The Lock interfaces provide additional functionalities over the synchronized
 keyword. One of the new functionalities is implemented by the tryLock() method.
 This method tries to get the control of the lock and if it can't, because it's used by
 other thread, it returns the lock. With the synchronized keyword, when a thread
 (A) tries to execute a synchronized block of code, if there is another thread (B)
 executing it, the thread (A) is suspended until the thread (B) finishes the execution
 of the synchronized block. With locks, you can execute the tryLock() method. This
 method returns a Boolean value indicating if there is another thread running the
 code protected by this lock.
 The Lock interfaces allow a separation of read and write operations having multiple
 readers and only one modifier.
 The Lock interfaces offer better performance than the synchronized keyword.

 * The constructor of the ReentrantLock and ReentrantReadWriteLock classes
 * admits a boolean parameter named fair that allows you to control the behavior
 * of both classes. The false value is the default value and it's called the
 * non-fair mode. In this mode, when there are some threads waiting for a lock (
 * ReentrantLock or ReentrantReadWriteLock ) and the lock has to select one of
 * them to get the access to the critical section, it selects one without any
 * criteria. The true value is called the fair mode. In this mode, when there
 * are some threads waiting for a lock ( ReentrantLock or ReentrantReadWriteLock
 * ) and the lock has to select one to get access to a critical section, it
 * selects the thread that has been waiting for the most time. Take into account
 * that the behavior explained previously is only used with the lock() and
 * unlock() methods. As the tryLock() method doesn't put the thread to sleep if
 * the Lock interface is used, the fair attribute doesn't affect its
 * functionality.
 */
public class ReentrantLockExample {

	public static class PrintQueue {
		private final Lock queueLock = new ReentrantLock();

		public void printJob(Object document) {
			queueLock.lock(); // get control on lock
			/*
			 * the Lock interface (and the ReentrantLock class) includes another
			 * method to get the control of the lock. It's the tryLock() method.
			 * The biggest difference with the lock() method is that this
			 * method, if the thread that uses it can't get the control of the
			 * Lock interface, returns immediately and doesn't put the thread to
			 * sleep. This method returns a boolean value, true if the thread
			 * gets the control of the lock, and false if not.
			 */
			try {
				Long duration = (long) (Math.random() * 10000);
				System.out.println(Thread.currentThread().getName()
						+ ":PrintQueue: Printing a Job during "
						+ (duration / 1000) + " seconds");
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				queueLock.unlock();
			}

		}
	}

	public static class Job implements Runnable {

		private PrintQueue printQueue;

		public Job(PrintQueue printQueue) {
			this.printQueue = printQueue;
		}

		@Override
		public void run() {
			System.out.printf("%s: Going to print a document\n", Thread
					.currentThread().getName());
			printQueue.printJob(new Object());
			System.out.printf("%s: The document has been printed\n", Thread
					.currentThread().getName());
		}

	}

	public static void main(String... args) {
		PrintQueue printQueue = new PrintQueue();
		Thread thread[] = new Thread[10];
		for (int i = 0; i < 10; i++) {
			thread[i] = new Thread(new Job(printQueue), "Thread " + i);
		}
		for (int i = 0; i < 10; i++) {
			thread[i].start();
		}
	}

}
