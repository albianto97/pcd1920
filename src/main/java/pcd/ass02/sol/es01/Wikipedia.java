package pcd.ass02.sol.es01;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import pcd.ass02.sol.common.*;

public class Wikipedia {

	private HttpClient client;
	private ExecutorService exec;
	
	public Wikipedia() {
		client = HttpClient.newHttpClient();
		exec = Executors.newFixedThreadPool(10);
	}
	
	/**
	 * Synchronous call for getting a concept from Wikipedia.
	 * 
	 * @param concept name
	 * @return the concept 
	 */
	public Optional<Concept> getConcept(final String concept) {
		final String co = concept.replaceAll(" ","_");
		String uri = "https://it.wikipedia.org/w/api.php?action=parse&page=" + co + "&format=json&section=0&prop=links";			
		Future<Concept> fut = exec.submit(() -> {
			try {
				HttpRequest request = HttpRequest.newBuilder()
				          .uri(URI.create(uri))
				          .build();
		
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				JsonObject obj = new JsonObject(response.body());
				
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
					return new Concept(concept, linkedConcepts);
				} else {
					return null;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		});		
		try {
			Concept con = fut.get();
			if (con != null) {
				return Optional.of(con);
			} else {
				return Optional.empty();
			}
		} catch (Exception ex) {
			return Optional.empty();
		}
	}
	
}
