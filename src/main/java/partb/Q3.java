package partb;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import util.Utils;

public class Q3 {

	public static void execute() throws Exception {

		// Get ElasticSearch client
		TransportClient client = Utils.getClient();

		SearchResponse response = client.prepareSearch("shakespeare")
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("play_name", "Othello"))	// Change this line to implement queries
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
