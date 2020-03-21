package de.awacademy.ourblog.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;


public class GetGPSfromApi {
    public Map<String, Double> getLocationFromCityStreetNumber(String city, String street, int number) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://open.mapquestapi.com/geocoding/v1/address?key="+apiKey.getApiKey()+"&city="+city+"&street="+street+"+"+number+"&maxResults=1", String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return new HashMap<String, Double>() {{
            put("lat", root.findPath("lat").asDouble());
            put("lng", root.findPath("lng").asDouble());
        }};

    }

}
