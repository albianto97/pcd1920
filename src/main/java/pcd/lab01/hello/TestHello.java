package pcd.lab01.hello;

public class TestHello {

	public static void main(String[] args) throws Exception {
		
		MyThread myThread = new MyThread("MyWorker");
		myThread.start();		
		
		String myName = Thread.currentThread().getName();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Thread spawned - I'm " + myName);
		
	}
}
