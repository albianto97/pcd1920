package pcd.lab04.monitors.latch;

import java.util.concurrent.locks.*;

/*
 * Latch - to be implemented
 */
public class LatchImpl2 implements Latch {

	private int count;
	private Lock mutex;
	private Condition gateOpened;
	
	public LatchImpl2(int count) {
		this.count = count;
		mutex = new ReentrantLock();
		gateOpened = mutex.newCondition();
	}


	/*
	Fino a che count è maggiore di zero blocca il passaggio poi fa passare quelli che si erano bloccati
	*/
	@Override
	public void await() throws InterruptedException {	
		try {
			mutex.lock();
			while (count > 0) {
				gateOpened.await();
			}
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public void countDown() {	
		try {
			mutex.lock();
			if (count > 0) {
				count--;
				printCount(count);
				if (count == 0) {
					gateOpened.signalAll();
				}
			}
		} finally {
			mutex.unlock();
		}
	}

	public void printCount(int count){
		System.out.println("count "+count);
	}

	
}
