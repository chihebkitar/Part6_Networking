package networking;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
public class Geocoder {
    private static final String BASE = "https://maps.googleapis.com/maps/api/geocode/json?";
    private static final String KEY = "AIzaSyDw_d6dfxDEI7MAvqfGXEIsEMwjC1PWRno";

    private Function<String, String> encoder = s -> {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    };
    // i need to encode the address
    // function of str coming in and out and invoke the encode method
    // spaces will be replaces bu + and strings will be joined by commas

    public String encodeAddress(List<String> address) {
        return address.stream()
                .map(encoder)
                .collect(Collectors.joining(","));
        // convert from string to string joined by comma
        //return encoded address
    }

    public String getData(List<String> address) {
        String encoded = encodeAddress(address);
        String response = "";
        try {
            URL url = new URL(String.format("%saddress=%s&key=%s", BASE, encoded, KEY));
            //full url
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openStream()))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    response += "\n" + line;
                    // to read our json data
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

// to work with geocoder u need to enable billing on "Google Maps Platform"
    // otherwise u git this error
    //   "error_message" : "You must enable Billing on the Google Cloud Project at https://console.cloud.google.com/project/_/billing/enable Learn more at https://developers.google.com/maps/gmp-get-started",


    public void fillInLatLng(Location location) {
        String encoded = encodeAddress(
                Arrays.asList(location.getStreet(), location.getCity(), location.getState()));
        // get s , c state from location and turn it to list
        //encode the add
        try {
            URL url = new URL(String.format("%saddress=%s&key=%s", BASE, encoded, KEY));
            try (InputStream is = url.openStream(); // will auto close
                 JsonReader jr = Json.createReader(is)) {
                JsonObject jo = jr.readObject();
                JsonObject loc = jo.getJsonArray("results")
                        .getJsonObject(0)
                        .getJsonObject("geometry")
                        .getJsonObject("location");
                location.setLatitude(loc.getJsonNumber("lat").doubleValue());
                location.setLongitude(loc.getJsonNumber("lng").doubleValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}