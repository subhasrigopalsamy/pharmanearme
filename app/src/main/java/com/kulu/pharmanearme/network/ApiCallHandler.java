package com.kulu.pharmanearme.network;

import com.google.gson.Gson;
import com.kulu.pharmanearme.models.PharmacyDetails;
import com.kulu.pharmanearme.models.PharmacyServicesDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by Subha on 13/07/2017.
 */

public class ApiCallHandler {

    public String makeServiceCall(String url){
        HttpURLConnection connection = null;
        String response = null;
        InputStream is = null;

        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app-version","1.5");
            connection.setRequestProperty("app-platform","android");
            connection.setRequestProperty("client-id","mzUFOsQJLESdYOY");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("user_id","221a1420-56a0-11e7-8ff8-a40f7533d1c6");
            connection.setRequestProperty("Token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl9pZCI6Ijk5NGNjNWUwLTVjMWEtMTFlNy1iYzY3LTEyMzhiOWM5NWI3ZiIsImV4cCI6MTUwNzMwNTQ5NiwiaWF0IjoxNDk4NjY1NDk2LCJ1c2VyX2lkIjoiMjIxYTE0MjAtNTZhMC0xMWU3LThmZjgtYTQwZjc1MzNkMWM2In0.5P3yqScrpfUISv6-nZ9owGmhsZcmpEvO8d5nKMiZEjU");
            connection.connect();
           // int responseCode = connection.getResponseCode();
            is = connection.getInputStream();
            response = readIt(is);
            //Log.d("response",response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    connection.disconnect();
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private String readIt(InputStream iStream) throws Exception {
        String singleLine = "";
        StringBuilder totalLines = new StringBuilder(iStream.available());
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(iStream));

            while ((singleLine = reader.readLine()) != null) {
                totalLines.append(singleLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return totalLines.toString();
    }

    public Observable<PharmacyDetails> getPharamcyList(final String url){

        return Observable.create(new ObservableOnSubscribe<PharmacyDetails>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PharmacyDetails> emitter) throws Exception {
                try{
                    Gson gson = new Gson();
                    PharmacyDetails pharmaDetails = gson.fromJson(makeServiceCall(url),PharmacyDetails.class);

                    emitter.onNext(pharmaDetails);
                    emitter.onComplete();
                }catch(Exception e){
                    emitter.onError(e);
                }
            }
        });
    }
    public Observable<PharmacyServicesDetails> getServicesList(final String url){

        return Observable.create(new ObservableOnSubscribe<PharmacyServicesDetails>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PharmacyServicesDetails> emitter) throws Exception {
                try{
                    Gson gson = new Gson();
                    PharmacyServicesDetails servicesDetails = gson.fromJson(makeServiceCall(url),PharmacyServicesDetails.class);
                    emitter.onNext(servicesDetails);
                    emitter.onComplete();
                }catch(Exception e){
                    emitter.onError(e);
                }
            }
        });



    }
}
