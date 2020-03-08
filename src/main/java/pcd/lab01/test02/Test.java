package pcd.lab01.test02;

/*
 * On the impact on the performance / CPU utilisation
 * of the agent behaviours 
 */
public class Test {

	public static void main(String[] args) {
		new MyWorkerB("worker-B").start();
		new MyWorkerA("worker-A").start();		
	}

}
