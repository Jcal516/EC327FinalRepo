package com.example.ec327_mapfunc;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;

import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ec327_mapfunc.databinding.ActivityMapsBinding;
import android.util.Log;
import android.content.Context;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener  {

    private GoogleMap mMap;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private ActivityMapsBinding binding; // ex code doesn't have it?
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String provider;
    protected String latitude, longitude;
    double lat;
    double longi;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        txtLat = (TextView) findViewById(R.id.db371d029c5a76e3);
        binding = ActivityMapsBinding.inflate(getLayoutInflater()); // ex code doesn't have it?
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.db371d029c5a76e3);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    // added
//        GoogleMapOptions options = new GoogleMapOptions()
//                .mapId("db371d029c5a76e3");
//        SupportMapFragment mapFragment = SupportMapFragment.newInstance(options);
//        MapView mapView = new MapView(context, options);


    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.db371d029c5a76e3);
        txtLat.setText(new StringBuilder().append("Latitude:").append(location.getLatitude()).append(", Longitude:").append(location.getLongitude()).toString());
        lat = location.getLatitude();
        longi = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMapsBinding.inflate(getLayoutInflater()); // ex code doesn't have it?
//        setContentView(binding.getRoot());

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.


//    class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//        @Override
//        protected void onCreate(@Nullable Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.main);
//        }
//    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng boston = new LatLng(42.35, -71.1);
        LatLng current = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(boston).title("Marker in Boston"));
        mMap.addMarker(new MarkerOptions().position(current).title("Marker in current"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(newLatLngZoom(boston,14.5f)); // zoom value between 1~20. 1 is world, 5 is landmass, 20 is buildings
        mMap.moveCamera(newLatLngZoom(current,14.5f));
    }
}
