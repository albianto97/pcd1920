package pcd.lab06.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class Test1_basic {

	public static void main(String[] args) {
		
		Vertx  vertx = Vertx.vertx();

		FileSystem fs = vertx.fileSystem();    		
		
		
		fs.readFile("build.gradle", (AsyncResult<Buffer> res) -> {
			log("BUILD \n" + res.result().toString().substring(0,160));
		});
	
		fs.readFile("settings.gradle", (AsyncResult<Buffer> res) -> {
			log("SETTINGS \n" + res.result().toString().substring(0,160));
		});	
	}
	
	private static void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

