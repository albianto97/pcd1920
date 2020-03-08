package pcd.lab01.test03;

public class MyWorkerA extends Worker {
	
	public MyWorkerA(String name){
		super(name);
	}
	
	public void run(){
		println("a1");
		sleepFor(5000);
		println("a2");
	}
	
}

