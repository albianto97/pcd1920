package pcd.lab02.lost_updates.sol2;

public class SafeCounter {

	private int cont;
	
	public SafeCounter(int base){
		this.cont = base;
	}
	
	public synchronized void inc(){
		cont++;
	}
	
	public int getValue(){
		return cont;
	}
}
