package pcd.lab02.lost_updates.sol_with_synchblock;

public class TestCounterSafe {

	public static void main(String[] args) throws Exception {
		int ntimes = 1000000; // Integer.parseInt(args[0]);
		
		SafeCounter c = new SafeCounter(0); //SAFE
		
		Worker w1 = new Worker(c,ntimes); //NO SYNCRO NEI WORKERS
		Worker w2 = new Worker(c,ntimes);

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
