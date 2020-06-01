package pcd.ass02.sol.es03;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import pcd.ass02.sol.common.*;

public class Wikipedia {

	private HttpClient client;
	
	public Wikipedia() {
		client = HttpClient.newHttpClient();
	}
	
	/**
	 * Build a stream with a concept retrieved from Wikipedia, if available,
	 * or an empty stream, if not.
	 * 
	 * @param concept
	 * @return
	 */
	public Flowable<Concept> getConcept(final String concept) {
		Flowable<Concept> source = Flowable.create(emitter -> {
			try {
				final String co = concept.replaceAll(" ","_");
				String uri = "https://it.wikipedia.org/w/api.php?action=parse&page=" + co + "&format=json&section=0&prop=links";			
				HttpRequest request = HttpRequest.newBuilder()
				          .uri(URI.create(uri))
				          .build();	
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				JsonObject obj = null;
				try {
					obj = new JsonObject(response.body());
				} catch (Exception ex) {}
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
						// System.out.println(linkedConcepts);
						emitter.onNext(new Concept(concept, linkedConcepts));
					}
				}
				emitter.onComplete();
			} catch (Exception ex) {
				// ex.printStackTrace();
				emitter.onComplete();
			}
		}, BackpressureStrategy.LATEST);
		return source;
	}
	
}
