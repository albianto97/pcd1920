package pcd.lab01.test00;

/*
 * Thread organization as simple agents.
 * 
 * Non determinism.
 * 
 */
public class Test {
	public static void main(String[] args) {
		new MyWorkerA("worker-A").start();		
		new MyWorkerB("worker-B").start();
	}
}
