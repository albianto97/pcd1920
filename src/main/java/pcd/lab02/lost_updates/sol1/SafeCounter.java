package pcd.lab02.lost_updates.sol1;

public class SafeCounter extends UnsafeCounter {

	private int cont;
	
	public SafeCounter(int base){
		super(base);
	}
	
	public void inc(){
		synchronized(this){
			cont++;
		}
	}
	
	public int getValue(){
		synchronized (this){
			return cont;
		}
	}
}
