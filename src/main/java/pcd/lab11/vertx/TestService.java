package pcd.lab11.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.WebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class TestService {

	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();
		WebClient client = WebClient.create(vertx);

		/* Doing a GET retrieving a JSON reply */		
		client
		  .get(12000, "localhost", "/api/state")
		  .addQueryParam("param", "param_value")
		  .send(ar -> {
		    if (ar.succeeded()) {
		      HttpResponse<Buffer> response = ar.result();
		      JsonObject body = response.bodyAsJsonObject();
		      System.out.println("Response: " + body);
		    } else {
		      System.out.println("Something went wrong " + ar.cause().getMessage());
		    }
		  });		

		JsonObject newState = new JsonObject().put("state", 11);
		
		/* Doing a PUT with a JSON msg */
		client
		  .put(12000, "localhost", "/api/resources/res100")
		  // .addQueryParam("param", "param_value")
		  .sendJsonObject(newState, ar -> {
		    if (ar.succeeded()) {
		      HttpResponse<Buffer> response = ar.result();
		      JsonObject body = response.bodyAsJsonObject();
		      System.out.println("Response: " + body);
		    } else {
		      System.out.println("Something went wrong " + ar.cause().getMessage());
		    }
		  });		

		/* Doing a PUT with a JSON msg */
		client
		  .post(12000, "localhost", "/api/actions/start")
		  .sendJsonObject(newState, ar -> {
		    if (ar.succeeded()) {
		      HttpResponse<Buffer> response = ar.result();
		      JsonObject body = response.bodyAsJsonObject();
		      System.out.println("Response: " + body);
		    } else {
		      System.out.println("Something went wrong " + ar.cause().getMessage());
		    }
		  });	
		
		/* WEB SOCKET */
		
		
		HttpClientOptions options = 
				new HttpClientOptions()
					.setDefaultHost("localhost")
					.setDefaultPort(12000);

		vertx.createHttpClient(options).webSocket("/api/events", res -> {
			  if (res.succeeded()) {
				  System.out.println("Connected!");				  
				  WebSocket ws = res.result();
				  ws.frameHandler(frame -> {
					  System.out.println(">> " + frame);
				  });
			  }
			});
			
		/*
		vertx.createHttpClient().websocket(12000,"localhost","/api/events", res -> {
			  // if (res.succeeded()) {
				  System.out.println("Connected!");				  
				  // WebSocket ws = res.result();
				  res.frameHandler(frame -> {
					  System.out.println(">> " + frame);
				  });
			  // }
			});*/
	}

}
