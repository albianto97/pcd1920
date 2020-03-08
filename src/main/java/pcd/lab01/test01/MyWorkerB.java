package pcd.lab01.test01;

public class MyWorkerB extends Worker {
	
	public MyWorkerB(String name){
		super(name);
	}

	public void run(){
		while (true){
		  action1();	
		  action2();
		}
	}
	
	protected void action1(){
		/*
		String s = "";
		for (int i = 0; i < 100000; i++){
			for (int j = 0; j <20000; j++){
				s = s+(((char)i) + ((char)j));
			}
		}*/
		println("b1");
		sleepForRandomTime(0,10);	
	}
	
	protected void action2(){
		/*
		String s = "";
		for (int i = 0; i < 100000; i++){
			for (int j = 0; j <20000; j++){
				s = s+(((char)i) + ((char)j));
			}
		}*/
		println("b2");
		sleepForRandomTime(100,200);	
	}
}
