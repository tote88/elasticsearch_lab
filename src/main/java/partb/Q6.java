package partb;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import util.Utils;

public class Q6 {

	public static void execute() throws Exception {
		// Get ElasticSearch client
		TransportClient client = Utils.getClient();

		// Build query and execute it
		SearchResponse response = client.prepareSearch("shakespeare")
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.setQuery(
						QueryBuilders.termsQuery("text_entry", "thou", "thee", "thy")
				)
				.setPostFilter(QueryBuilders.termQuery("play_name", "Hamlet"))
				.setSize(Utils.MAX_RESULTS)
				.setExplain(true)
				.get();

		// Iterate through query results and print them
		SearchHits hits = response.getHits();
		int hitsCount = hits.getHits().length;
		System.out.println("Query executed successfully. "
				+ "\nQuery Time: " + response.getTookInMillis() + " ms"
				+ "\nResults Count: " + hitsCount
				+ "\nResults: ");

		for (int i = 0; i < hitsCount; i++) {
			SearchHit hit = hits.getAt(i);
			System.out.println("[" + hit.getScore() + "] "
					+ hit.getSourceAsString());
		}

		// IMPORTANT // Close ElasticSearch client
		Utils.closeClient(client);
	}

}
