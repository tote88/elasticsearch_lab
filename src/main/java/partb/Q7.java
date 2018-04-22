package partb;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import util.Utils;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

public class Q7 {

	public static void execute() throws Exception {
		// Get ElasticSearch client
		TransportClient client = Utils.getClient();

		// Build query and execute it
		SearchResponse response = client.prepareSearch("shakespeare")
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.setQuery(
						QueryBuilders.termQuery("text_entry", "love")
				)
				.addAggregation(
						terms("speakers").field("speaker").order(Terms.Order.count(false))
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

		/*for (int i = 0; i < hitsCount; i++) {
			SearchHit hit = hits.getAt(i);
			System.out.println("[" + hit.getScore() + "] "
					+ hit.getSourceAsString());
		}*/
		Terms terms = response.getAggregations().get("speakers");
		for (Terms.Bucket entry : terms.getBuckets()) {
			System.out.println(entry.getKey() + " " +       // Term
					entry.getDocCount()); // Doc count
		}
		/*SignificantTerms agg = response.getAggregations().get("speaker");

		// For each entry
		for (SignificantTerms.Bucket entry : agg.getBuckets()) {
			System.out.println(entry.getKey() + " " +       // Term
			entry.getDocCount()); // Doc count
		}*/

		// IMPORTANT // Close ElasticSearch client
		Utils.closeClient(client);
	}

}
