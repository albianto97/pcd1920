package pcd.lab02.lost_updates;

public class TestCounter {

	public static void main(String[] args) throws Exception {
		int ntimes = 100000; // Integer.parseInt(args[0]);
		UnsafeCounter c = new UnsafeCounter(0);
		Worker w1 = new Worker(c,ntimes);
		Worker w2 = new Worker(c,ntimes);

		Cron cron = new Cron();
		cron.start(); //tempo di partenza
		w1.start();
		w2.start();
		w1.join();
		w2.join();
		cron.stop(); //tempo di fine
		System.out.println("Counter final value: "+c.getValue()+" in "+cron.getTime()+"ms.");
	}
}
