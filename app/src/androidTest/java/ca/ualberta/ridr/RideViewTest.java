package ca.ualberta.ridr;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.robotium.solo.Solo;

import org.junit.Test;

import java.util.Date;

/**
 * Created by Justin on 2016-11-12.
 */

public class RideViewTest extends ActivityInstrumentationTestCase2<RideView> {

    private Solo solo;
    /**
     * Instantiates a new Lonely twitter activity test.
     */
    public RideViewTest() {
        super(RideView.class);
    }

    /**
     * Test start.
     *
     * @throws Exception the exception
     */
    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }
//      A map should load
//    public void initializeMap(){
//        RideView maps = (RideView) solo.getCurrentActivity();
//        solo.assertCurrentActivity("Wrong activity", RideView.class);
//        //make sure map has no marker
//        assertEquals(maps.countMarkers(), 0);
//    }
//    A user should be able to see a ride and there should be more than to markers
//    public void getDriver Rides(){
//        RideView rideView = (RideView) solo.getCurrentActivity();
//        rideView.setTest(true);
//
//        // Set user
//        rideList.setUser("kristy");
//        rideList.setUset(someRideID);
//        solo.assertCurrentActivity("Wrong activity", GeoView.class);
//
//        // Check to see the user has more than one ride
//        RideController controller = rideList.getRideController();
//        assert(controller.size() > 0);
//        assert(rideView.markers > 0);
//    }
//
//    @Override
//    public void tearDown() throws Exception{
//        solo.finishOpenedActivities();
//    }

}
