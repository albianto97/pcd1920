package pcd.lab11.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.core.buffer.Buffer;

public class MyService extends AbstractVerticle {

	private int localPort;
	private long idEvents;
	
	public MyService(int port) {
		this.localPort = port;	
		idEvents = 0;
	}

	@Override
	public void start() {	
		setup();
		log("Ready");
	}

	private void setup() {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());		
		router.get("/api/state").handler(this::handleGetState);
		router.put("/api/resources/:resID").handler(this::handleUpdateResState);
		router.post("/api/actions/start").handler(this::handleNewActionStart);

		getVertx()
			.createHttpServer()
			.requestHandler(router)
			// .websocketHandler(this::webSocketHandler)			
			.webSocketHandler(this::webSocketHandler)			
			.listen(localPort);
	}
	
	private void handleGetState(RoutingContext ctx) {
		log("New GetState from " + ctx.request().absoluteURI());
		HttpServerResponse response = ctx.response();
		JsonObject obj = new JsonObject();
		obj.put("state", 10);
		response.putHeader("content-type", "application/json").end(obj.encodePrettily());
	}

	private void handleUpdateResState(RoutingContext ctx) {
		log("NewUpdateState from " + ctx.request().absoluteURI());
		HttpServerResponse response = ctx.response();
		String resId = ctx.request().getParam("resID");
		log("resId " + resId);
		JsonObject newState = ctx.getBodyAsJson();
		log("newState " + newState);
		JsonObject obj = new JsonObject();
		obj.put("newState", newState);
		obj.put("resId", resId);
		response.putHeader("content-type", "application/json").end(obj.encodePrettily());
	}

	
	private void handleNewActionStart(RoutingContext ctx) {
		HttpServerResponse response = ctx.response();
		// physicalAsset.doCmdStartHeating();
		JsonObject obj = new JsonObject();
		JsonObject links = new JsonObject();
		int newActionId = 13;
		links.put("href", "/api/actions/start/" + newActionId);
		obj.put("links", links);
		response.putHeader("content-type", "application/json").end(obj.encodePrettily());
		response.end();
	}
	
	private void webSocketHandler(ServerWebSocket ws) {
		 if (ws.path().equals("/api/events")) {
			 	getVertx().setPeriodic(1000, id -> {
					JsonObject obj = new JsonObject();
					obj.put("event", idEvents++);
					Buffer buffer = Buffer.buffer();
					obj.writeToBuffer(buffer);
					ws.write(buffer);
					log("sent " + obj);
				});
		 } else {
			 ws.reject();
		 }
	}
	
	public void log(String msg) {
		System.out.println("[SERVICE] " + msg);
	}

	public static void main(String[] args) {
		AbstractVerticle service = new MyService(12000);
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(service);
	}
	
}