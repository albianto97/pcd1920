package pcd.ass02.sol.es02;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;
import java.util.LinkedList;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import pcd.ass02.sol.common.Concept;

public class Wikipedia {

	private Vertx vertx;
	private HttpClient client;

	public Wikipedia(Vertx vertx) {
		this.vertx = vertx;
		client = HttpClient.newHttpClient();
	}

	public Future<Concept> getConcept(final String concept) {
		final String co = concept.replaceAll(" ", "_");		
		String uri = "https://it.wikipedia.org/w/api.php?action=parse&page=" + co + "&format=json&section=0&prop=links";
		Promise<Concept> promise = Promise.promise();
		vertx.executeBlocking(pro -> {
			try {
				HttpRequest request = HttpRequest.newBuilder()
				          .uri(URI.create(uri))
				          .build();
		
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				JsonObject obj = new JsonObject(response.body());
				pro.complete(obj);
			} catch (Exception ex) {
				ex.printStackTrace();
				pro.complete(null);
			}
		}, false, res -> {
			JsonObject obj = (JsonObject) (res.result());
			if (obj != null) {
				obj = obj.getJsonObject("parse");
				if (obj != null) {
					JsonArray links = obj.getJsonArray("links");
					Iterator<Object> it = links.iterator();
					LinkedList<String> linkedConcepts = new LinkedList<String>();
					while (it.hasNext()) {
						JsonObject o = (JsonObject) it.next();
						int ns = o.getInteger("ns");
						if (ns == 0) {
							linkedConcepts.add(o.getString("*"));
						}
					}
					promise.complete(new Concept(concept, linkedConcepts));
				} else {
					promise.complete(null);
				}
			}
		});

		return promise.future();
	}

}
