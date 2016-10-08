import java.util.Iterator;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;


public class QueryTable {

	public static void main(String[] args) {
		/*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (/Users/ghemant/.aws/credentials).
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/ghemant/.aws/credentials), and is in valid format.",
                    e);
        }
        
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		
		// This client will default to US West (Oregon)
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
		client.setRegion(usWest2);
		
		DynamoDB dynamoDB = new DynamoDB(client);
		
		Table table = dynamoDB.getTable("Reply");
		
		QuerySpec spec = new QuerySpec()
	    .withKeyConditionExpression("Id = :v_id")
	    .withValueMap(new ValueMap()
	        .withString(":v_id", "Amazon DynamoDB#DynamoDB Thread 1"));

		ItemCollection<QueryOutcome> items = table.query(spec);
	
		Iterator<Item> iterator = items.iterator();
		Item item = null;
		while (iterator.hasNext()) {
		    item = iterator.next();
		    System.out.println(item.toJSONPretty());
		}

	}

}
