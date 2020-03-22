package pcd.lab03.to_be_synch;

public class Pinger extends Thread {

	public Pinger() {
	}	
	
	public void run() {
		while (true) {
			try {
				System.out.println("ping!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}