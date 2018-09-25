package training.busboard;

import org.glassfish.jersey.jackson.JacksonFeature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class TransportClient {
	private static final String APP_ID = "13e431c6";
	private static final String APP_KEY = "51d315696579eb1f1ff9b35c3bd25f7d";
    private static final String API_URL = "http://transportapi.com";
    private static Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    
    public static Coordinates getLatLong(String postcode) 
    {
    	return client.target("http://api.postcodes.io")
    			.path("/postcodes")
    			.queryParam("q", postcode)
    			.request(MediaType.APPLICATION_JSON_TYPE)
    			.get(PostcodeResponse.class)
    			.result.get(0);
    }
    public static List<BusStop> getBusStops(Coordinates coords) 
    {
    	return client.target("http://transportapi.com")
    			.path("/v3/uk/places.json")
    			.queryParam("lat", coords.latitude)
    			.queryParam("lon", coords.longitude)
    			.queryParam("type", "bus_stop")
    			.queryParam("app_id", APP_ID)
    			.queryParam("app_key", APP_KEY)
    			.queryParam("limit", 2)
    			.request()
    			.get(BusStopsResponse.class)
    			.member;
    }
    
    public List<ArrivalPrediction> getArrivalPredictions(String atcocode) 
    {
         return client.target(API_URL)
                 .path("/v3/uk/bus/stop/" + atcocode + "/live.json")
                 .queryParam("app_id", APP_ID)
                 .queryParam("app_key", APP_KEY)
                 .queryParam("group", "no")
                 .queryParam("limit", 5)
                 .request(MediaType.APPLICATION_JSON_TYPE)
                 .get(NextBusesResult.class)
                 .getArrivalPredictions();
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class NextBusesResult {
    	public Departures departures;
    	
    	private List<ArrivalPrediction> getArrivalPredictions() 
    	{
    		return departures.all;
    	}
    }
    
    private static class Departures 
    {
    	public List<ArrivalPrediction> all;
    }
}
