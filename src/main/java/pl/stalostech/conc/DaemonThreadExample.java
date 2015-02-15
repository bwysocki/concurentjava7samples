package pl.stalostech.conc;

import java.util.concurrent.TimeUnit;

/**
 * Java has a special kind of thread called daemon thread. These kind of threads
 * have very low priority and normally only executes when no other thread of the
 * same program is running. When daemon threads are the only threads running in
 * a program, the JVM ends the program finishing these threads
 * 
 */
public class DaemonThreadExample implements Runnable{

	public static void main(String... args) {
		Thread t = new Thread(new DaemonThreadExample());
		t.start();
		//exception because of end of execution
	}
	
	public DaemonThreadExample(){
		Thread.currentThread().setDaemon(true);
	}
	
	public void run() {
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
