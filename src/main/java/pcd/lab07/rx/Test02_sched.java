package pcd.lab07.rx;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Test02_sched {

	public static void main(String[] args) throws Exception {

		System.out.println("\n=== TEST No schedulers ===\n");
		
		Observable
		.just(100)	
		.map(v -> { log("map 1 " + v); return v + 1; })	// by current thred (main)
		.map(v -> { log("map 2 " + v); return v + 1; })	// by current thred (main)
		.map(v -> v + 1)						// by the current thread
		.subscribe(v -> {						// by the current thread
			log("sub " + v);
		});
		
		System.out.println("\n=== TEST observeOn ===\n");

		/* observeOn:
		 * 
		 * "Modifies an ObservableSource to perform its emissions 
		 * and notifications on a specified Scheduler, asynchronously 
		 * with an unbounded buffer with Flowable.bufferSize() "island size"."
		 */
		Observable
		.just(100)	
		.map(v -> { log("map 1 " + v); return v * v; })		// by the current thread (main thread)
		.observeOn(Schedulers.computation()) 				// => use RX comp thread(s) downstream
		.map(v -> { log("map 2 " + v); return v + 1; })		// by the RX comp thread
		.subscribe(v -> {						// by the RX comp thread
			log("sub " + v);
		});

		Thread.sleep(100);

		System.out.println("\n=== TEST observeOn with blockingSubscribe ===\n");

		Observable
		.just(100)	
		.map(v -> { log("map 1 " + v); return v * v; })		// by the current thread (main thread)
		.observeOn(Schedulers.computation()) 				// => use RX comp thread(s) downstream
		.map(v -> { log("map 2 " + v); return v + 1; })		// by the RX comp thread
		.blockingSubscribe(v -> {							// by the current thread (main thread = invoker)
			log("sub " + v);
		});

		System.out.println("\n=== TEST subscribeOn ===\n");

		/* 
		 * subscribeOn:
		 * 
		 * "Asynchronously subscribes Observers to this ObservableSource on the specified Scheduler."
		 */
		Observable<Integer> src = Observable
		.just(100)	
		.map(v -> { log("map 1 " + v); return v * v; })		
		.map(v -> { log("map 2 " + v); return v + 1; });		

		src
		.subscribeOn(Schedulers.computation()) 	
		.subscribe(v -> {									
			log("sub 1 " + v);
		});

		src
		.subscribeOn(Schedulers.computation()) 	
		.subscribe(v -> {									
			log("sub 2 " + v);
		});

		Thread.sleep(100);
		
		System.out.println("\n=== TEST observeOn with multiple subs ===\n");

		Observable<Integer> src2 = Observable
		.just(100)	
		.map(v -> { log("map 1 " + v); return v * v; })		// by the current thread (main thread)
		.observeOn(Schedulers.computation()) 				// => use RX comp thread(s) downstream
		.map(v -> { log("map 2 " + v); return v + 1; });		// by the RX comp thread;
		
		src2.subscribe(v -> {						// by the RX comp thread
			log("sub 1 " + v);
		});

		src2.subscribe(v -> {						// by the RX comp thread
			log("sub 2 " + v);
		});

		Thread.sleep(100);

		System.out.println("\n=== TEST parallelism  ===\n");

		/* 
		 * running independent flows and merging their results back into a single flow 
		 * warning: flatMap => no order in merging
		 */

		Flowable.range(1, 10)
		  .flatMap(v ->
		      Flowable.just(v)
		        .subscribeOn(Schedulers.computation())
				.map(w -> { log("map " + w); return w * w; })		// by the RX comp thread;
		  )
		  .blockingSubscribe(v -> {
			 log("sub > " + v); 
		  });
		
	}
		
	static private void log(String msg) {
		System.out.println("[" + Thread.currentThread().getName() + "] " + msg);
	}
	
}
