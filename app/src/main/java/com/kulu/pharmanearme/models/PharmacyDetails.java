package com.kulu.pharmanearme.models;

/**
 * Created by Subha on 13/07/2017.
 */

public class PharmacyDetails {

    private Pharmacy[] data;

    public Pharmacy[] getAllPharmacy(){
        return data;
    }

    public class Pharmacy {
        private String place_type;
        private String place_title;
        private String place_id;
        private float lon;
        private float lat;
        private float current_distance;

        public String getPlaceType() {
            return place_type;
        }

        public String getPlaceTitle() {
            return place_title;
        }

        public String getPlaceID() {
            return place_id;
        }

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }

        public float getDistance() {
            return Math.round(current_distance * 100) / 100;
        }
    }

}
