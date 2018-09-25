package training.busboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrivalPrediction {
    private String lineName;
    private String direction;
    private String bestDepartureEstimate;

    private ArrivalPrediction() {}

    public String getLineName() 
    {
        return lineName;
    }
    public String getDirection() 
    {
        return direction;
    }

    public String getBestDepartureEstimate() 
    {
        return bestDepartureEstimate;
    }
}
