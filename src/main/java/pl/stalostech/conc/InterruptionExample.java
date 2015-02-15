package pl.stalostech.conc;

public class InterruptionExample extends Thread {

	public static void main(String... args) {
		InterruptionExample interruptionExample = new InterruptionExample();
		interruptionExample.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		interruptionExample.interrupt();
	}

	@Override
	public void run() {
		long number = 1L;
		while (true) {
			if (isPrime(number)) {
				System.out.printf("Number %d is Prime", number);
			}
			if (isInterrupted()) {
				System.out.printf("The Prime Generator has been Interrupted");
				return;
				/*
				 * Thread can ignore its interruption, but this is not the
				 * expected behaviour. Java provides also the
				 * InterruptedException exception for this purpose. You can
				 * throw this exception when you detect the interruption of the
				 * thread and catch it in the run() method. There is an
				 * important difference between the isInterrupted() and the
				 * interrupted() methods. The first one doesn't change the value
				 * of the interrupted attribute, but the second one sets it to
				 * false. As the interrupted() method is a static method, the
				 * utilization of the isInterrupted() method is recommended.
				 */
			}
			number++;
		}
	}

	private boolean isPrime(long number) {
		if (number <= 2) {
			return true;
		}
		for (long i = 2; i < number; i++) {
			if ((number % i) == 0) {
				return false;
			}
		}
		return true;
	}
}
