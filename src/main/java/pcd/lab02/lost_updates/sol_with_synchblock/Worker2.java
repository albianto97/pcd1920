package pcd.lab02.lost_updates.sol_with_synchblock;


public class Worker2 extends Thread {
	
	private UnsafeCounter counter;
	private int ntimes;
	
	public Worker2(UnsafeCounter c, int ntimes){
		counter = c;
		this.ntimes = ntimes;
	}
	
	public void run(){
		for (int i = 0; i < ntimes; i++){
			synchronized (counter) { 	//SE COMMENTIAMO QUESTO SBAGLIA IL
				counter.inc();			//IL NUMERO DI ITERAZIONI
			}
		}
	}
}
