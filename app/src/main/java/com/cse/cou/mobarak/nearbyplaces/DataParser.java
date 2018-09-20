package com.cse.cou.mobarak.nearbyplaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mobarak on 8/20/2018.
 */

public class DataParser {
    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");

            }
            if (!googlePlaceJson.isNull("vicinity")) {
                placeName = googlePlaceJson.getString("vicinity");

            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");
            googlePlacesMap.put("place_name", placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latitude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("reference", reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return googlePlacesMap;
    }
    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int count=jsonArray.length();
        List<HashMap<String,String>> placeslist=new ArrayList<>();
        HashMap<String,String> placeMap=null;
        for (int i=0;i<count;i++){
            try {
                placeMap=getPlace((JSONObject) jsonArray.get(i));
                placeslist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeslist;

    }
    public List<HashMap<String,String>> parse(String jsondata){
        JSONArray jsonArray=null;
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(jsondata);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);

    }
}
