package pcd.lab07.rx;

import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.core.*;

public class Test05_time_flow {

	public static void main(String[] args) throws Exception {

		long startTime = System.currentTimeMillis(); 
		
		log("Generating.");
		Observable
        		.interval(100, TimeUnit.MILLISECONDS)	// Long
        		.timestamp() 							// Timed
        		.sample(500, TimeUnit.MILLISECONDS)
				.map(ts -> " " + (ts.time(TimeUnit.MILLISECONDS) - startTime) + "ms: " + ts.value())
				.take(10)
				.subscribe(Test05_time_flow::log);		
		
		log("Going to sleep.");
		Thread.sleep(10000);
		log("Done.");
		
	}

	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + " ] " + msg);
	}
	
}
