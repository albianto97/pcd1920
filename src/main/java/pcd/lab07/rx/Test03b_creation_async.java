package pcd.lab07.rx;

import io.reactivex.rxjava3.core.*;

public class Test03b_creation_async {

	public static void main(String[] args) throws Exception {

		log("Creating an observable (cold) using its own thread.");

		Observable<Integer> source = Observable.create(emitter -> {		     
			new Thread(() -> {
				int i = 0;
				while (i < 10){
					try {
						log("source: "+i); 
						emitter.onNext(i);
						Thread.sleep(200);
						i++;
					} catch (Exception ex){}
				}
			}).start();
		 });

		Thread.sleep(1000);
		
		log("Subscribing A.");
		
		source.subscribe((s) -> {
			log("Subscriber A: " + s); 
		});	

		Thread.sleep(1000);

		log("Subscribing B.");

		source.subscribe((s) -> {
			log("Subscriber B: " + s); 
		});	

		System.out.println("Done.");
	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + "  ] " + msg);
	}

}
