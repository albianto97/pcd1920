package pcd.lab07.rx;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Test05_backpressure {

	public static void main(String[] args) throws Exception {

		log("creating hot observable.");

		Flowable<Long> source = genHotStream(5);

		log("subscribing .");

		source.onBackpressureBuffer(5, () -> {
			log("HELP!");
		})
				// .onBackpressureBuffer(5, () -> { log("HELP!"); },
				// BackpressureOverflowStrategy.DROP_OLDEST)
				.observeOn(Schedulers.computation())
				.subscribe(Test05_backpressure::compute, Throwable::printStackTrace);

		Thread.sleep(1000_000);
	}

	private static Flowable<Long> genHotStream(long delay) {
		Flowable<Long> source = Flowable.create(emitter -> {
			new Thread(() -> {
				long i = 0;
				try {
					while (true) {
						if (i % 10 == 0) {
							log("emitting: " + i);
						}
						emitter.onNext(i);
						i++;
						if (delay > 0) {
							try {
								Thread.sleep(delay);
							} catch (Exception ex) {
							}
						}
					}
				} catch (Exception ex) {
					log("exit");
				}
			}).start();
		}, BackpressureStrategy.BUFFER);

		ConnectableFlowable<Long> hotObservable = source.publish();
		hotObservable.connect();
		return hotObservable;
	}

	private static void compute(Long v) {
		try {
			log("compute v: " + v);
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static private void log(String msg) {
		System.out.println("[" + Thread.currentThread().getName() + " ] " + msg);
	}

}
