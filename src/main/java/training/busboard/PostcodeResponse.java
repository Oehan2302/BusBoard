package training.busboard;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostcodeResponse {
	public List<Coordinates> result;
}
