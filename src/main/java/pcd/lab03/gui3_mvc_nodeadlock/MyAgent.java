package pcd.lab03.gui3_mvc_nodeadlock;

public class MyAgent extends Thread {

	private MyModel model;
	
	public MyAgent(MyModel model){
		this.model = model;
	}
	
	public void run(){
		while (true){
			try {
				model.update();
				Thread.sleep(500);
			} catch (Exception ex){
			}
		}
	}
}
