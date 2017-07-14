package com.kulu.pharmanearme.models;

/**
 * Created by Subha on 13/07/2017.
 */

public class PharmacyServicesDetails {

    private Services[] data;

    public Services[] getAllServices(){
        return data;
    }

    public class Services {
        private String pharmacy_id;
        private String service_id;
        private String service_name;
        private String service_icon;
        private String service_description;


        public String getServiceName() {
            return service_name;
        }

        public String getServiceIcon() {
            return service_icon;
        }

        public String getServiceDesc() {
            return service_description;
        }

        public String getServiceID() {
            return service_id;
        }

        public String getPharmacyID() {
            return pharmacy_id;
        }



    }

}
