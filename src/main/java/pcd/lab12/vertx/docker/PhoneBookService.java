package pcd.lab12.vertx.docker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class PhoneBookService extends AbstractVerticle {

	private int localPort;

	@SuppressWarnings("serial")
	private List<User> phonebook = new ArrayList<User>() {{
		add(new User("AA01", "Mario Rossi", "00 123445678"));
		add(new User("AA02", "Luca Verdi", "05 98765432"));
		add(new User("AA03", "Laura Bianchi", "07 45671234"));
	}};

	public PhoneBookService(final int port) {
		this.localPort = port;
	}

	@Override
	public void start() {
		setup();
		log("Ready");
	}

	private void setup() {
		final Router router = Router.router(vertx);
		
		final Set<String> allowHeaders = new HashSet<>();
		allowHeaders.add("x-requested-with");
		allowHeaders.add("Access-Control-Allow-Origin");
		allowHeaders.add("origin");
		allowHeaders.add("Content-Type");
		allowHeaders.add("accept");

		final Set<HttpMethod> allowMethods = new HashSet<>();
		allowMethods.add(HttpMethod.GET);
		allowMethods.add(HttpMethod.POST);
		allowMethods.add(HttpMethod.DELETE);
		allowMethods.add(HttpMethod.PUT);
		
		router.route().handler(CorsHandler.create("*").allowedHeaders(allowHeaders).allowedMethods(allowMethods));
		
		router.route().handler(BodyHandler.create());
		router.get("/api/phonebook").handler(this::handleGetAll);
		router.get("/api/phonebook/:id").handler(this::handleGetById);
		router.post("/api/phonebook").handler(this::handleAddUser);
		router.put("/api/phonebook/:id").handler(this::handleUpdateUserPhone);
		router.delete("/api/phonebook/:id").handler(this::handleDeleteUser);

		//System.out.println(getClass().getClassLoader().getResource("webroot").toString());
		
		router.route().handler(StaticHandler.create().setWebRoot("webroot/lab12").setCachingEnabled(false));
		
		getVertx().createHttpServer().requestHandler(router).listen(localPort);
		
		
	}

	private void handleGetAll(final RoutingContext routingContext) {
		final JsonArray array = new JsonArray();

		phonebook.forEach(user -> {
			array.add(user.toJson());
		});

		routingContext.response()
				.putHeader(C.HTTP.HeaderElement.CONTENT_TYPE, C.HTTP.HeaderElement.ContentType.APPLICATION_JSON)
				.setStatusCode(C.HTTP.ResponseCode.OK)
				.end(array.encodePrettily());
	}

	private void handleGetById(final RoutingContext routingContext) {
		final String id = routingContext.request().getParam("id");

		final Optional<User> user = phonebook.stream().filter(u -> u.getId().contentEquals(id)).findFirst();

		if (user.isPresent()) {
			routingContext.response()
				.putHeader(C.HTTP.HeaderElement.CONTENT_TYPE, C.HTTP.HeaderElement.ContentType.APPLICATION_JSON)
				.setStatusCode(C.HTTP.ResponseCode.OK)
				.end(user.get().toString());
		} else {
			routingContext.response()
				.setStatusCode(C.HTTP.ResponseCode.NOT_FOUND)
				.end();
		}
	}

	private void handleAddUser(final RoutingContext routingContext) {
		
		final User user = new User((JsonObject) Json.decodeValue(routingContext.getBodyAsString()));

		phonebook.add(user);

		routingContext.response().setStatusCode(C.HTTP.ResponseCode.CREATED)
				.putHeader(C.HTTP.HeaderElement.CONTENT_TYPE, C.HTTP.HeaderElement.ContentType.APPLICATION_JSON)
				.end(Json.encodePrettily(user));
	}

	private void handleUpdateUserPhone(final RoutingContext routingContext) {
		final String id = routingContext.request().getParam("id");

		final Optional<User> user = phonebook.stream().filter(u -> u.getId().contentEquals(id)).findFirst();

		if (user.isPresent()) {
			final JsonObject newPhone = (JsonObject) Json.decodeValue(routingContext.getBodyAsString());
			
			user.get().setPhone(newPhone.getString("phone"));
			
			routingContext.response()
					.putHeader(C.HTTP.HeaderElement.CONTENT_TYPE, C.HTTP.HeaderElement.ContentType.APPLICATION_JSON)
					.setStatusCode(C.HTTP.ResponseCode.OK).end(user.get().toString());
			
		} else {
			routingContext.response()
			.setStatusCode(C.HTTP.ResponseCode.NOT_FOUND)
			.end();
		}
	}

	private void handleDeleteUser(final RoutingContext routingContext) {
		final String id = routingContext.request().getParam("id");
		
		final Optional<User> user = phonebook.stream().filter(u -> u.getId().contentEquals(id)).findFirst();

		if (user.isPresent()) {
			phonebook.remove(user.get());
			
			routingContext.response()
				.setStatusCode(C.HTTP.ResponseCode.OK)
				.end();
			
		} else {
			routingContext.response()
				.setStatusCode(C.HTTP.ResponseCode.NOT_FOUND)
				.end();
		}
	}

	private void log(final String msg) {
		System.out.println("[" + getClass().getSimpleName() + "] " + msg);
	}

	public static void main(final String[] args) {
		Vertx.vertx().deployVerticle(new PhoneBookService(8080));
	}
}
