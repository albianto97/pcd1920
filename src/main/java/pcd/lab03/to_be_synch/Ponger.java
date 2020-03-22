package pcd.lab03.to_be_synch;

public class Ponger extends Thread {
	
	public Ponger() {
	}	
	
	public void run() {
		while (true) {
			try {
				System.out.println("pong!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}