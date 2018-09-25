package training.busboard;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;

public class Main {
    public static void main(String args[]) 
    {
        String id = promptForPostCode();
        Coordinates coords = TransportClient.getLatLong(id);
        List<BusStop> busStops = TransportClient.getBusStops(coords);

        for (BusStop busStop : busStops) {
        	System.out.println();
        	System.out.println("Bus stop: " + busStop.name);
	        List<ArrivalPrediction> arrivalPredictions = new TransportClient().getArrivalPredictions(busStop.atcocode);

	        arrivalPredictions.stream().sorted(Comparator.comparing(ArrivalPrediction::getBestDepartureEstimate)).limit(5).forEach(prediction ->
	        	System.out.println(formatPrediction(prediction))
	        );
        }

    }
	private static String promptForPostCode()
	{
		System.out.print("Enter your postcode: ");
		return new Scanner(System.in).nextLine();
	}

    private static String formatPrediction(ArrivalPrediction prediction) 
    {
        return String.format("%s to %s will leave at: %s",
                prediction.getLineName(),
                prediction.getDirection(),
                prediction.getBestDepartureEstimate());
    }
}	
