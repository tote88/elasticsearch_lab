import partb.ExampleQuery;
import partb.Q1;
import partb.Q2;
import partb.Q3;
import partb.Q4;
import partb.Q5;
import partb.Q6;
import partb.Q7;
import partb.Q8;

/**
 * Hello world!
 *
 */
public class UPCSchool_ElasticSearch {

    public static void main( String[] args ) throws Exception {
		
    	if (args.length != 1) {
			throw new Exception("Wrong number of parameters, usage: <example | q1 | q2 | q3 | q4 | q5 | q6 | q7 | q8>)");
		}
    	String q = args[0];
    	
    	if (q.equals("example")) {
    		ExampleQuery.execute();
    	} else if (q.equals("q1")) {
    		Q1.execute();
    	} else if (q.equals("q2")) {
    		Q2.execute();
    	} else if (q.equals("q3")) {
    		Q3.execute();
    	} else if (q.equals("q4")) {
    		Q4.execute();
    	} else if (q.equals("q5")) {
    		Q5.execute();
    	} else if (q.equals("q6")) {
    		Q6.execute();
    	} else if (q.equals("q7")) {
    		Q7.execute();
    	} else if (q.equals("q8")) {
    		Q8.execute();
    	}
    }
}
