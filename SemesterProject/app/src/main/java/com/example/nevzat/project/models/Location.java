package com.example.nevzat.project.models;

/**
 * Created by nevzat on 18/12/15.
 */
public class Location {
    private double lat;
    private double lng;
    private LocationType locationType;

    public Location(){

    }
    public Location(double lat,double lng,LocationType locationType){
        this.lat = lat;
        this.lng = lng;
        this.locationType = locationType;

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
}
