package pl.stalostech.conc.criticalsection;

/**
 * Only one execution thread will access one of the methods of an object
 * declared with the synchronized keyword. If another thread tries to access any
 * method declared with the synchronized keyword of the same object, it will be
 * suspended until the first thread finishes the execution of the method. Static
 * methods have a different behavior. Only one execution thread will access one
 * of the static methods declared with the synchronized keyword, but another
 * thread can access other non- static methods of an object of that class. You
 * have to be very careful with this point, because two threads can access two
 * different synchronized methods if one is static and the other one is not. If
 * both methods change the same data, you can have data inconsistency errors.
 *
 */
public class SynchronizedExample implements Runnable {

	public static class Account {
		public double balance;

		/**
		 * If a thread (A) is executing a synchronized method and another thread
		 * (B) wants to execute other synchronized methods of the same object,
		 * it will be blocked until the thread (A) ends. But if threadB has
		 * access to different objects of the same class, none of them will be
		 * blocked.We can use the synchronized keyword to protect the access to
		 * a block of code instead of an entire method.When you use the
		 * synchronized keyword in this way, you must pass an object reference
		 * as a parameter
		 */
		public synchronized void addAmount(double amount) {
			double tmp = balance;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tmp += amount;
			balance = tmp;
		}

		public synchronized void subtractAmount(double amount) {
			double tmp = balance;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tmp -= amount;
			balance = tmp;
		}
	}

	public static class Company implements Runnable {

		private Account account;

		public Company(Account account) {
			this.account = account;
		}

		public void run() {
			for (int i = 0; i < 100; i++) {
				account.addAmount(1000);
			}
		}
	}

	private Account account;

	public SynchronizedExample(Account account) {
		this.account = account;
	}

	public static void main(String... args) {
		SynchronizedExample.Account account = new SynchronizedExample.Account();
		account.balance = 1000;

		SynchronizedExample.Company company = new SynchronizedExample.Company(
				account);
		Thread companyThread = new Thread(company);

		SynchronizedExample bank = new SynchronizedExample(account);
		Thread bankThread = new Thread(bank);

		System.out.printf("Account : Initial Balance: %f\n", account.balance);
		companyThread.start();
		bankThread.start();

		try {
			companyThread.join();
			bankThread.join();
			System.out.printf("Account : Final Balance: %f\n", account.balance);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			account.subtractAmount(1000);
		}
	}

}
