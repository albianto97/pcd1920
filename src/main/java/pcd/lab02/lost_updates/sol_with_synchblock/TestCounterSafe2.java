package pcd.lab02.lost_updates.sol_with_synchblock;

public class TestCounterSafe2 {

	public static void main(String[] args) throws Exception {
		int ntimes = 1000000; // Integer.parseInt(args[0]);
		
		UnsafeCounter c = new UnsafeCounter(0); //UNSAFE
		
		Worker2 w1 = new Worker2(c,ntimes);  //USA WORKER CON SYNCRONISED
		Worker2 w2 = new Worker2(c,ntimes);

		Cron cron = new Cron();
		cron.start();
		w1.start();
		w2.start();
		w1.join();
		w2.join();
		cron.stop();
		System.out.println("Counter final value: "+c.getValue()+" in "+cron.getTime()+"ms.");
	}
}
