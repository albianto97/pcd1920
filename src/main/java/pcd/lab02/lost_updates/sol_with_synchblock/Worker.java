package pcd.lab02.lost_updates.sol_with_synchblock;


public class Worker extends Thread{
	
	private UnsafeCounter counter;
	private int ntimes;
	
	public Worker(UnsafeCounter c, int ntimes){
		counter = c;
		this.ntimes = ntimes;
	}
	
	public void run(){
		for (int i = 0; i < ntimes; i++){
			counter.inc();
		}
	}
}
