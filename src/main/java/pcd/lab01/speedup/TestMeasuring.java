package pcd.lab01.speedup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestMeasuring {

	public static void main(String[] args) {
		int howMany = Runtime.getRuntime().availableProcessors();
		System.out.println("N cores: "+howMany);
		long t0 = System.currentTimeMillis();
		
		List<Thread> tlist = new ArrayList<Thread>();
		/*tlist.forEach(tt -> {
				System.out.println("tlist" + tt);
		});*/ // NON stampa perchè questa è solo la creazione
		IntStream.rangeClosed(0, howMany-1).forEach(i -> {  //Ho 12 processori - 1
			Thread t = new Thread(() -> {
				System.out.println("Hello from core " + i + " - " + Thread.currentThread().getName());
				double waste = 0;
				for (int j = 0; j < 100000; j++){
					for (int k = 0; k < 1000; k++){
						waste = waste + k*Math.sin(j);
					}
				}
			});
			t.start();
			tlist.add(t);
		});
		
		tlist.stream().forEach(t -> {
			try {
				System.out.println(t);
				t.join();
			} catch (Exception ex){
			}
		});
		
		long t1 = System.currentTimeMillis();
		System.out.println("Time elapsed: "+ (t1-t0));
	}

}
