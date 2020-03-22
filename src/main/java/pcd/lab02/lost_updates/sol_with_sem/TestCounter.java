package pcd.lab02.lost_updates.sol_with_sem;

import java.util.concurrent.Semaphore;

public class TestCounter {

	public static void main(String[] args) throws Exception {
		int ntimes = 10000000; // Integer.parseInt(args[0]);
		UnsafeCounter c = new UnsafeCounter(0);
		
		Semaphore mutex = new Semaphore(1);
		
		Worker w1 = new Worker(c,ntimes, mutex);
		Worker w2 = new Worker(c,ntimes, mutex);

		Cron cron = new Cron();
		cron.start();
		w1.start();
		w2.start();
		w1.join();
		w2.join();
		cron.stop();
		System.out.println("Counter final value: "+c.getValue()+" in "+cron.getTime()+"ms.");
	}
}
