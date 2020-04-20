package pcd.lab06.vertx;

import io.vertx.core.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class Test5_composeq {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		FileSystem fs = vertx.fileSystem();   
		
		Future<Buffer> f1 = Future.future(promise -> 
			fs.readFile("build.gradle", promise)
		);
		
		Future<Void> fut = f1.compose(b1 -> {
			log("BUILD => \n" + b1.toString().substring(0,160));
			return Future.<Buffer>future(promise -> fs.readFile("settings.gradle", promise));
		}).compose((Buffer b2) -> {
			log("SETTINGS => \n" + b2.toString().substring(0,160));
			return Future.succeededFuture();
		}, (Throwable t) -> {
			log("FAILURE");
			return Future.failedFuture(t);
		});
		
		fut.onComplete((AsyncResult<Void> res) -> {
			log("completed.");
		});
	}

	private static void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

