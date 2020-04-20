package pcd.lab06.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

class MyVerticle extends AbstractVerticle {
	
	public void start() {
		FileSystem fs = getVertx().fileSystem();    		
		
		fs.readFile("build.gradle", (AsyncResult<Buffer> res) -> {
			log("BUILD \n" + res.result().toString().substring(0,160));
		});
	
		fs.readFile("settings.gradle", (AsyncResult<Buffer> res) -> {
			log("SETTINGS \n" + res.result().toString().substring(0,160));
		});
	}

	private void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

public class Test2_withverticle {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		vertx.deployVerticle(new MyVerticle());
	}
}

