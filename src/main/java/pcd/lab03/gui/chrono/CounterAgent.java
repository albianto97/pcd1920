package pcd.lab03.gui.chrono;

public class CounterAgent extends Thread{
	private Counter counter;
	private volatile boolean stopped;
	
	public CounterAgent(Counter c){
		counter = c;
	}
	public void run(){
		stopped = false;
		while (!stopped){
			counter.inc();
			System.out.println(counter.getValue());
			try {
				Thread.sleep(10);
			} catch(Exception ex){
			}
		}
	}
	
	public void notifyStop(){
		super.interrupt();
		stopped = true;
	}
}
