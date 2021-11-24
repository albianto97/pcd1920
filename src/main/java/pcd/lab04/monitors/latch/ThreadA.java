package pcd.lab04.monitors.latch;

import java.util.Random;

public class ThreadA extends Thread {

	private Latch latch;
	
	public ThreadA(String name, Latch latch) {
		super(name);
		this.latch = latch;
	}
	
	public void run() {
		try {
			log("waiting opening.");
			//int c = 0;
			//c = increase(c);
			latch.await();
			//c = decrease(c);
			log("opened."); // 
		} catch (InterruptedException ex) {
			log("Interrupted!");
		}
	}
	
	private void log(String msg) {
		synchronized(System.out) {
			System.out.println("[ "+getName()+" ] "+msg);
		}
	}
	
	private void waitFor(long ms) throws InterruptedException{
		Thread.sleep(ms);
	}

	public int increase(int c){
		c++;
		System.out.println(c);
		return c;
	}
	public int decrease(int c){
		c--;
		System.out.println(c);
		return c;
	}
}
