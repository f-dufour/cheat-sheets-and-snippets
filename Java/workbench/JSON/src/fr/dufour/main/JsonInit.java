package fr.dufour.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonInit {
	
	static Map<String, Map<String, ArrayList<Double>>> jsonReferenceMasses;

	public static MzPair getMzPair(String polymer, String typeOfPair) {
		ArrayList<Double> mzCode = jsonReferenceMasses.get(polymer).get(typeOfPair);
		return new MzPair(mzCode.get(0), mzCode.get(1));
	}	
	
	static void initJsonParser() throws JsonParseException, JsonMappingException, IOException {
		File file = new File("src/fr/dufour/main/referenceMasses.json");
		ObjectMapper om = new ObjectMapper();
		jsonReferenceMasses = om.readValue(file, new TypeReference<Map <String, Map<String, ArrayList<Double>>>>() {});
	}
	
}
