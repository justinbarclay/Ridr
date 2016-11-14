package ca.ualberta.ridr;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by mackenzie on 12/10/16.
 */
public class Driver extends User {
    @JestId
    private String elasticID;

    // These are all marked transient as we don't want them serialized when we pass this object to
    // our AsyncDatabaseController
    private transient Vehicle vehicle;
    private transient String bankAccountNo;
    private transient ArrayList<Ride> rideArrayList;

    public Driver(String name, Date dateOfBirth, String creditCard,
                  String email, String phoneNumber, Vehicle vehicle) {
        super(name, dateOfBirth, creditCard, email, phoneNumber);
        this.vehicle = vehicle;
        this.rideArrayList = new ArrayList<Ride>();
        this.setDriverStatus(true);
    }

    public String getElasticID() {
        return elasticID;
    }

    public void setElasticID(String elasticID) {
        this.elasticID = elasticID;
    }

    public ArrayList<Ride> getRides() {
        return rideArrayList;
    }

    public void setRides(ArrayList<Ride> rideArrayList) {
        this.rideArrayList = rideArrayList;
    }

    public void addRide() {
    }

    public boolean kewordSearch(String input) {
        return false;
    }

    public boolean geoSort(String s, String myLocation) {
        return false;
    }

    public void acceptRide(Ride ride) {
    }

    public boolean completeRide(Ride ride) {
        return false;
    }

    public boolean isPayed() {
        return false;
    }

    public boolean getPendingRides() {
        return false;
    }

    public boolean getCompletedRides() {
        return false;
    }

    public boolean riderAcceptedRide(Ride ride) {
        return false;
    }

    public boolean isOffline() {
        return false;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}


