package com.cse.cou.mobarak.nearbyplaces;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mobarak on 8/20/2018.
 */

public class GetNearbyPlacesData  extends AsyncTask<Object,String,String>{
    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {
        mMap= (GoogleMap) objects[0];
        url= (String) objects[1];


        DownloadUri downloadUri=new DownloadUri();
        try {
            googlePlacesData=downloadUri.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList=null;
        DataParser dataParser=new DataParser();
        nearbyPlaceList=dataParser.parse(s);
        showNearbyPlaces(nearbyPlaceList);

    }
    public void showNearbyPlaces(List<HashMap<String,String>> nearplacesList){
        for(int i=0;i<nearplacesList.size();i++){
            MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String,String> googlePlace=nearplacesList.get(i);
            String placeName=googlePlace.get("place_name");
            String vicinity=googlePlace.get("vicinity");
            double lat=Double.parseDouble(googlePlace.get("lat"));
            double lng=Double.parseDouble(googlePlace.get("lng"));
            LatLng latLng=new LatLng(lat,lng);

            markerOptions.position(latLng);
            markerOptions.title(placeName+" : "+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));


            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        }


    }
}
