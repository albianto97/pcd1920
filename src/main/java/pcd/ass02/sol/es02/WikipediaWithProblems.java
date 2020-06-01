package pcd.ass02.sol.es02;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import pcd.ass02.sol.common.Concept;

public class WikipediaWithProblems {

	private Vertx vertx;
	private WebClient client;

	public WikipediaWithProblems(Vertx vertx) {
		this.vertx = vertx;
		client = WebClient.create(vertx);
	}

	public Future<Concept> getConcept(final String concept) {
		final String co = concept.replaceAll(" ", "_");
		String uri = "/w/api.php?action=parse&page=" + co + "&format=json&section=0&prop=links";
		Promise<Concept> promise = Promise.promise();

		client.get(80, "it.wikipedia.org", uri).send(ar -> {
			if (ar.succeeded()) {
				try {
					HttpResponse<Buffer> response = ar.result();
					JsonObject obj = response.bodyAsJsonObject();
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
				} catch (Exception ex) {
					promise.complete(null);
				}
			} else {
				promise.complete(null);
			}
		});

		return promise.future();
	}

}
