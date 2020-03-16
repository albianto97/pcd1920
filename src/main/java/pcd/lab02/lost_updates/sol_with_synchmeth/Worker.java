package pcd.lab02.lost_updates.sol_with_synchmeth;

import java.util.concurrent.Semaphore;

public class Worker extends Thread{
	
	private SafeCounter counter;
	private int ntimes;
	private Semaphore mutex;
	
	public Worker(SafeCounter c, int ntimes, Semaphore mutex){
		counter = c;
		this.ntimes = ntimes;
		this.mutex = mutex;
	}
	
	public void run(){
		for (int i = 0; i < ntimes; i++){
			try {
				mutex.acquire();
				counter.inc();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			} finally {
				mutex.release();
			}
		}
	}
}
