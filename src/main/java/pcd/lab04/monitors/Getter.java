package pcd.lab04.monitors;

public class Getter extends Worker {
	
	private SynchCell2 cell;
	
	public Getter(SynchCell2 cell){
		super("getter");
		this.cell = cell;
	}
	
	public void run(){
		log("before getting");
		int value = cell.get();
		log("got value:"+value);
	}
}
