package pcd.lab01.test01;

/*
 * Non terminating behaviours.
 * 
 */
public class Test {

	public static void main(String[] args) {
		new MyWorkerB("worker-B").start();
		new MyWorkerA("worker-A").start();		
	}

}
