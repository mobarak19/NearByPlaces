package com.cse.cou.mobarak.nearbyplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    GoogleMap mMap;

    Object datatranfer[] = new Object[2];

    String url = "";
    int PROXIMITY_RADIUS = 5000;
    double latitude , longitude ;
    LocationManager locationManager;
    LocationListener locationListener;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       Location l= getLocation();
       latitude=l.getLatitude();
       longitude=l.getLongitude();
        sharedPreferences=getSharedPreferences("nearbyplaces123",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
//                editor.putFloat("lat",(float)latitude);
//                editor.putFloat("lng",(float)longitude);
//                editor.commit();
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
               Manifest.permission.INTERNET},10);
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);


                return;
            }
        }else {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if (requestCode>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

                }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_normal) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            LatLng dhaka=new LatLng(latitude,longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }else if(id==R.id.action_hybride){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            LatLng dhaka=new LatLng(latitude,longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }else if(id==R.id.action_satelite){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            LatLng dhaka=new LatLng(latitude,longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }else if(id==R.id.action_search_range){


        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_atm) {

            showPlaces("atm");

        } else if (id == R.id.nav_amusement_park) {
            showPlaces("amusement_park");

        }
        else if (id == R.id.nav_bank) {
            showPlaces("bank");

        } else if (id == R.id.nav_cafe) {
            showPlaces("cafe");

        } else if (id == R.id.nav_car_rental) {
            showPlaces("car_rental");

        }else if (id == R.id.nav_car_wash) {
            showPlaces("car_wash");

        }else if (id == R.id.nav_car_repair) {
            showPlaces("car_repair");

        }
        else if (id == R.id.nav_church) {
            showPlaces("church");

        }
        else if (id == R.id.nav_dentist) {
            showPlaces("dentist");

        }
        else if (id == R.id.nav_department_store) {
            showPlaces("department_store");

        }
        else if (id == R.id.nav_electritian) {
            showPlaces("electritian");

        } else if (id == R.id.nav_embasy) {
            showPlaces("embasy");

        }
        else if (id == R.id.nav_furniture_store) {
            showPlaces("furniture_store");

        }
        else if (id == R.id.nav_fire_station) {
            showPlaces("fire_station");

        }else if (id == R.id.nav_gym) {
            showPlaces("gym");

        }else if (id == R.id.nav_gas_station) {
            showPlaces("gas_station");

        }
        else if (id == R.id.nav_hindu_temple) {
            showPlaces("hindu_temple");


        }else if (id == R.id.nav_home_goods_store) {
            showPlaces("home_goods_store");


        }
        else if (id == R.id.nav_hospital) {
            showPlaces("hospital");


        }else if (id == R.id.nav_jewelry_store) {
            showPlaces("jewelry_store");

        }
        else if (id == R.id.nav_laundry) {
            showPlaces("laundry");

        }
        else if (id == R.id.nav_library) {
            showPlaces("library");

        }
        else if (id == R.id.nav_locksmith) {
            showPlaces("locksmith");

        }else if (id == R.id.nav_museum) {
            showPlaces("museum");

        }else if (id == R.id.nav_mosque) {
            showPlaces("mosque");

        }else if (id == R.id.nav_park) {
            showPlaces("park");

        }else if (id == R.id.nav_parking) {
            showPlaces("parking");

        }else if (id == R.id.nav_pharmacy) {
            showPlaces("pharmacy");

        }else if (id == R.id.nav_plamber) {
            showPlaces("plamber");

        }else if (id == R.id.nav_police) {
            showPlaces("police");

        }else if (id == R.id.nav_pet_store) {
            showPlaces("pet_store");

        }else if (id == R.id.nav_physiotherapist) {
            showPlaces("physiotherapist");

        }
        else if (id == R.id.nav_restaurant) {
            showPlaces("restaurant");

        }else if (id == R.id.nav_supermarket) {
            showPlaces("supermarket");

        }else if (id == R.id.nav_school) {
            showPlaces("school");

        }else if (id == R.id.nav_shopping_mall) {
            showPlaces("shopping_mall");

        }else if (id == R.id.nav_spa) {
            showPlaces("spa");

        }else if (id == R.id.nav_subway_station) {
            showPlaces("subway_station");

        }
        else if (id == R.id.nav_travel_agency) {
            showPlaces("travel_agency");

        } else if (id == R.id.nav_train_station) {
            showPlaces("train_station");

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng myplace=new LatLng(latitude,longitude);


        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(myplace);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myplace));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));


    }

    public void showPlaces(String n){
        mMap.clear();
        String name=n;

        url=getUrl(latitude,longitude,name);
        datatranfer[0]=mMap;
        datatranfer[1]=url;
        GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
        getNearbyPlacesData.execute(datatranfer);

        name=name.replaceAll("_"," ");
        String cap_name=name.substring(0,1).toUpperCase()+name.substring(1);
        setTitle("Nearby "+cap_name);
        Toast.makeText(MainActivity.this,"Showing Nearby "+cap_name,Toast.LENGTH_LONG).show();
    }
    private String getUrl(double lat,double lng,String name){
        StringBuilder stringBuilder=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+lat+","+lng);
        stringBuilder.append("&radius="+PROXIMITY_RADIUS);
        stringBuilder.append("&type="+name);
        stringBuilder.append("&sensor=true");
        stringBuilder.append("&key="+"AIzaSyATIG3qTKGXnze2fidq8UaGpAq5Cj4T-vs");
        return stringBuilder.toString();


    }
    public Location getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                return lastKnownLocationGPS;
            } else {
                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                return loc;
            }
        } else {
            return null;
        }
    }
}
