package partb;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import util.Utils;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

public class Q8 {

	public static void execute() throws Exception {
		// Get ElasticSearch client
		TransportClient client = Utils.getClient();

		// Build query and execute it
		SearchResponse response = client.prepareSearch("shakespeare")
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.addAggregation(
						terms("text_entries").field("text_entry").order(Terms.Order.count(false))
				)
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

		Terms terms = response.getAggregations().get("text_entries");
		for (Terms.Bucket entry : terms.getBuckets()) {
			System.out.println(entry.getKey() + " appears " + entry.getDocCount() +  " times.");
		}

		// IMPORTANT // Close ElasticSearch client
		Utils.closeClient(client);
	}

}
