package ca.ualberta.ridr;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mackenzie on 12/10/16.
 *
 * This object is a Ride object. It represents a ride between a rider and a driver between two locations.
 *
 */
public class Ride {
    private String pickup;
    private String dropoff;
    private Date rideDate;
    private String driver;
    private String rider;
    private Boolean isCompleted; //pending is denoted by isCompleted = False
    private LatLng pickupCoords;
    private LatLng dropOffCoords;
    private Boolean paid;
    private UUID id;
    private float fare;


    public LatLng getPickupCoords() {
        return pickupCoords;
    }

    public LatLng getDropOffCoords() {
        return dropOffCoords;
    }

    public Ride(String driverName, String riderName, String pickup, String dropoff, Date date, LatLng pickupCoords, LatLng dropOffCoords, Float fare){
        this.rideDate = date;
        this.driver = driverName;
        this.rider = riderName;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.pickupCoords = pickupCoords;
        this.dropOffCoords = dropOffCoords;
        this.isCompleted = false;
        this.paid = false;
        this.id = UUID.randomUUID();
        this.fare  = fare;
    }


    public float getFare() {
        return fare;
    }


    public UUID getId(){return id;}

    public String getPickupAddress() {
        return pickup;
    }

    public Date getRideDate() {
        return rideDate;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver.getName();
    }

    public String getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider.getName();
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public String getDropOffAddress() {
        return dropoff;
    }

    public boolean equals(Ride ride) {
        return this.id.equals(ride.id);
    }

    //stolen directly from Justin's implementation in request, then made sure custom to ride attributes
    //and database ride properties

    /**
     * Makes a Ride object to a Json formatted String.
     * Attempt to convert request into a JsonObject.
     * If fail return a null pointer.
     *
     * @return Json formatted String
     */
    public String toJsonString(){
        //
        // Need to use the java standard JSON object here because we are nesting JSON items
        JSONObject toReturn = new JSONObject();
        try {
            toReturn.put("rider", this.rider);
            toReturn.put("driver", this.driver);
            toReturn.put("pickup", this.pickup);
            toReturn.put("dropoff", this.dropoff);
            toReturn.put("pickupCoords", buildGeoPoint(pickupCoords));
            toReturn.put("dropOffCoords", buildGeoPoint(dropOffCoords));
            toReturn.put("id", this.id.toString());
            toReturn.put("isCompleted", this.isCompleted);
            toReturn.put("date", rideDate.toString());
            toReturn.put("isPaid", paid);
            toReturn.put("fare", fare);
            return toReturn.toString();
        } catch(Exception e){
            Log.d("Error", String.valueOf(e));
            return null;

        }
    }
    public boolean isPaid(){
        return paid;
    }

    /**
     * Take a jsonObject as input and creates request out of it's keys
     */
    public Ride(JsonObject ride) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

        this.rider = ride.get("rider").getAsString();
        this.driver = ride.get("driver").getAsString();
        this.pickup = ride.get("pickup").getAsString();
        this.dropoff = ride.get("dropoff").getAsString();
        this.dropOffCoords = buildLatLng(ride.getAsJsonObject("dropOffCoords"));
        this.pickupCoords = buildLatLng(ride.getAsJsonObject("pickupCoords"));
        this.isCompleted = ride.get("isCompleted").getAsBoolean();
        this.rideDate = formatter.parse(ride.get("date").getAsString());
        this.id = UUID.fromString(ride.get("id").getAsString());
        this.paid = ride.get("isPaid").getAsBoolean();
        this.fare = ride.get("fare").getAsFloat();

    }

    private JSONObject buildGeoPoint(LatLng coords) throws JSONException {
        JSONObject newLatLng = new JSONObject();
        newLatLng.put("lat", coords.latitude);
        newLatLng.put("lon", coords.longitude);
        return newLatLng;

    }
    private LatLng buildLatLng(JsonObject coords){
        return new LatLng(coords.get("lat").getAsDouble(), coords.get("lon").getAsDouble());
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
