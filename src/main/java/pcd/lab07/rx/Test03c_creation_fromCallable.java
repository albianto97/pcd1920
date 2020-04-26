package pcd.lab07.rx;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Test03c_creation_fromCallable {

	public static void main(String[] args) throws Exception {

		System.out.println("\n=== TEST fromCallable | main thread ===\n");

		Flowable.fromCallable(() -> {
		    log("started.");
		    Thread.sleep(1000); 
		    log("completed.");
		    return "Done";
		})
		.subscribe(s -> {
			log("result: " + s);
		});

		System.out.println("\n=== TEST fromCallable | schedulers ===\n");

		Flowable.fromCallable(() -> {
		    log("started.");
		    Thread.sleep(1000); 
		    log("completed.");
		    return "Done";
		})
		.subscribeOn(Schedulers.computation()) 	// use a background thread for the callable
		.observeOn(Schedulers.io())				// use a io thread for subscribers
		.subscribe(s -> {
			log("result: " + s);
		});

		
		Thread.sleep(2000); // <--- wait for the flow to finish
	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + "  ] " + msg);
	}

}
