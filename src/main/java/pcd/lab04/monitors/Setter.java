package pcd.lab04.monitors;

public class Setter extends Worker {
	
	private SynchCell2 cell;
	private int value;
	
	public Setter(SynchCell2 cell, int value){
		super("setter");
		this.cell = cell;
		this.value = value;
	}
	
	public void run(){
		log("before setting");
		cell.set(value);
		log("after setting");
	}
}
