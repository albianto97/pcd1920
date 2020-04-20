package pcd.lab06.vertx;

import java.io.BufferedReader;
import java.io.FileReader;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class Test6_withblocking {

	public static void main(String[] args) {
		
		Vertx  vertx = Vertx.vertx();
		
		FileSystem fs = vertx.fileSystem();    		
		
		fs.readFile("build.gradle", (AsyncResult<Buffer> res) -> {
			log("BUILD \n" + res.result().toString().substring(0,160));
		});
	
		vertx.executeBlocking(promise -> {
			  // Call some blocking API that takes a significant amount of time to return
			  try {
				  BufferedReader br = new BufferedReader(new FileReader("settings.gradle"));
				  StringBuffer buf = new StringBuffer();
				  while (br.ready()) {
					  buf.append(br.readLine() + "\n");
				  }
				  promise.complete(buf.toString());
			  } catch (Exception ex) {
				  promise.fail("exception");
			  }
			}, res -> {
				if (res.succeeded()) {
					log("SETTINGS \n" + res.result().toString().substring(0,160));
				} else {
					log("EXCEPTION ");
				}
			});
	}
	
	private static void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

