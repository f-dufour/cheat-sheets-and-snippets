import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
		List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});

		String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});

		System.out.println(map.get("color"));
	}
	
	public static class Car {
		 
	    private String color;
	    private String type;
	    
		public String getColor() {
			return color;
		}
		public String getType() {
			return type;
		}
	 
	    
	}

}


