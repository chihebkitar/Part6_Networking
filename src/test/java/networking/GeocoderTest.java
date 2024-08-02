package networking;


import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class GeocoderTest {
    private Geocoder geocoder = new Geocoder();

    @Test
    public void encodeAddress() throws Exception {
        String encoded = geocoder.encodeAddress(
                Arrays.asList("1600 Ampitheatre Parkway", "Mountain View", "CA"));
        assertEquals("1600+Ampitheatre+Parkway,Mountain+View,CA", encoded);
    }

    @Test
    public void getData() throws Exception {
        String data = geocoder.getData(
                Arrays.asList("1600 Ampitheatre Parkway", "Mountain View", "CA"));
        System.out.println(data);
        assertThat(data, both(containsString("37.42")).and(containsString("-122.08")));
    }



}