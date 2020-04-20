package pcd.lab06.vertx;
import java.util.Random;

import io.vertx.core.*;

public class Test3_promise_future {
	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();

		log("pre");
		
		Promise<Double> promise = Promise.promise();
		
		vertx.setTimer(1000, res -> {
			log("timeout from the timer...");
			Random rand = new Random();
			double value = rand.nextDouble();
			if (value > 0.5) {
				log("...complete with success.");
				promise.complete(value);
			} else {
				log("...complete with failure.");
				promise.fail("Value below 0.5 " + value);
			}
		});
		
		Future<Double> fut = promise.future();
		
		fut.onSuccess((Double res) -> {
			log("reacting to timeout - success: " + res);
		});

		fut.onFailure((Throwable t) -> {
			log("reacting to timeout - failure: " + t.getMessage());
		});

		fut.onComplete((AsyncResult<Double> res) -> {
			log("reacting to completion - " + res.succeeded());
		});
		
		log("post");
	}
		
	private static void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

