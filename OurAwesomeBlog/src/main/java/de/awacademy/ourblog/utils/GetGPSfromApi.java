package de.awacademy.ourblog.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;


public class GetGPSfromApi {
    public static HashMap<String, Double> getLocationFromCityStreetNumber(String city, String street, int number) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://open.mapquestapi.com/geocoding/v1/address?key="+apiKey.getApiKey()+"&city="+city+"&street="+street+"+"+number+"&maxResults=1", String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return new HashMap<String, Double>() {{
            put("lat", root.findPath("lat").asDouble());
            put("lng", root.findPath("lng").asDouble());
        }};
    }
    public static double distanceBetweenAddresses(AddressForGPS addressForGPSOne, AddressForGPS addressForGPSTwo){
        HashMap<String, Double> posOne = new HashMap<>();
        HashMap<String, Double> posTwo = new HashMap<>();
        try{
            posOne = getLocationFromCityStreetNumber(addressForGPSOne.getCityName(), addressForGPSOne.getStreetName(), addressForGPSOne.getNumber());
            posTwo = getLocationFromCityStreetNumber(addressForGPSTwo.getCityName(), addressForGPSTwo.getStreetName(), addressForGPSTwo.getNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        double lon1 = posOne.get("lng");
        double lon2 = posTwo.get("lng");
        double lat1 = posOne.get("lat");
        double lat2 = posTwo.get("lat");
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 111.1895;
        return dist;
    }
}
