package pcd.lab01.hello;

public class TestHello2 {

	public static void main(String[] args) throws Exception {
		
		new Thread(() -> {
			System.out.println("Hello concurrent world! by " + Thread.currentThread().getName());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			System.out.println("done.");
		}).start();		
		
		String myName = Thread.currentThread().getName();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("Thread spawned - I'm " + myName);
		
	}
}
