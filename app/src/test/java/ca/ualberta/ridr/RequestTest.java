package ca.ualberta.ridr;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by Justin on 2016-11-03.
 */

public class RequestTest {
    @Test
    public void testRequestEquals(){
        Rider rider = new Rider("Steve", new Date(), "321", "goodemail", "9999999");
        Request request = new Request(rider, "University of Alberta", "10615 47 Avenue Northwest, Edmonton", new LatLng(53.525288, -113.525454), new LatLng(53.484775, -113.505067), new Date() );
        Request request = new Request(rider, "Edmonton", "Timbuktu", coords, coords, new Date());
        assertTrue(request.equals(request));
    }


}
