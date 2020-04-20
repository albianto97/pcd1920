package pcd.lab06.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class Test4_compo {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		FileSystem fs = vertx.fileSystem();    		

		Promise<Buffer> p1 = Promise.promise();
		Promise<Buffer> p2 = Promise.promise();

		Future<Buffer> f1 = p1.future();
		Future<Buffer> f2 = p2.future();
		
		fs.readFile("build.gradle", p1);
		fs.readFile("settings.gradle", p2);
				
		CompositeFuture.all(f1,f2).onSuccess((CompositeFuture res) -> {
			log("COMPOSITE => \n"+res.result().list());			
		}); 
	}

	private static void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

