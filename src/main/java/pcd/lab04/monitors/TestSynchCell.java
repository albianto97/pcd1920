package pcd.lab04.monitors;

public class TestSynchCell {
		
	public static void main(String args[]){
		
		SynchCell2 cell = new SynchCell2();
		new Getter(cell).start();
		new Getter(cell).start();
		new Getter(cell).start();
		
		try {
			Thread.sleep(2000);
		} catch (Exception ex){}
		new Setter(cell,303).start();
	}
}
