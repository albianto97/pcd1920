package pcd.lab07.rx;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;

public class Test03d_creation_hot {

	public static void main(String[] args) throws Exception {

		log("creating the source.");
		
		Flowable<Integer> source = Flowable.create(emitter -> {		     
			new Thread(() -> {
				int i = 0;
				while (i < 200){
					try {
						log("source: "+i); 
						emitter.onNext(i);
						Thread.sleep(10);
						i++;
					} catch (Exception ex){}
				}
			}).start();
		     //emitter.setCancellable(c::close);
		 }, BackpressureStrategy.BUFFER);
		
		ConnectableFlowable<Integer> hotObservable = source.publish();
		hotObservable.connect();
	
		Thread.sleep(500);
		log("Subscribing A.");
		
		hotObservable.subscribe((s) -> {
			log("subscriber A: "+s); 
		});	
		
		Thread.sleep(500);
		log("Subscribing B.");
		
		hotObservable.subscribe((s) -> {
			log("subscriber B: "+s); 
		});	
		
		log("Done.");

	}
	
	static private void log(String msg) {
		System.out.println("[ " + Thread.currentThread().getName() + "  ] " + msg);
	}
	

}
