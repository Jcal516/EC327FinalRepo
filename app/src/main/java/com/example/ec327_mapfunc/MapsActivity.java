package com.example.ec327_mapfunc;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ec327_mapfunc.databinding.ActivityMapsBinding;
import android.provider.Settings;
import android.util.Log;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener {
    private LocationManager locationManager;
    private GoogleMap mMap;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.db371d029c5a76e3);
//        MapFragment mapFragment = MapFragment.newInstance(
//                new GoogleMapOptions()
//                        .mapId(getResources().getString(R.string.map_id)));
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager()
//                        .findFragmentById(R.id.db371d029c5a76e3);
//        MapView mapView = new MapView(context, options);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default

        // user defines the criteria
        criteria.setCostAllowed(false);
        // get the best provider depending on the criteria
        String provider = locationManager.getBestProvider(criteria, false);

        // the last known location of this provider
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
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        MyLocationListener mylistener = new MyLocationListener();

        if (location != null) {
            mylistener.onLocationChanged(location);
        } else {
//            // leads to the settings because there is no last known location
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        // location updates: at least 1 meter and 200millsecs change
        locationManager.requestLocationUpdates(provider, 2000, 1, mylistener);
        Location location1 = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String a = "Location Change " + location1.getLatitude() + location1.getLongitude();
        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
        }
    };

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields
            Toast.makeText(MapsActivity.this, "Location Update: " + location.getLatitude() + location.getLongitude(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(MapsActivity.this, provider + "'s status changed to " + status + "!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MapsActivity.this, "Provider " + provider + " enabled!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MapsActivity.this, "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }

//        final LocationListener locationListener = new LocationListener();
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
//
    }

    @Override
    public void onLocationChanged(Location location) {
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
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location_new = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location_new.getLongitude();
        latitude = location_new.getLatitude();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
////        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        MyLocationListener mylistener = new MyLocationListener();

        LatLng boston = new LatLng(50.35, -72.1);
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(boston).title("Marker in Boston"));
        mMap.addMarker(new MarkerOptions().position(current).title("Marker current"));
        mMap.moveCamera(newLatLngZoom(boston, 14.5f)); // zoom value between 1~20. 1 is world, 5 is landmass, 20 is buildings
        mMap.moveCamera(newLatLngZoom(current, 15f));
    }

}


//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//
//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.api.model.PlaceLikelihood;
//import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
//import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
//import com.google.android.libraries.places.api.net.PlacesClient;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * An activity that displays a map showing the place at the device's current location.
// */
//public class MapsActivityCurrentPlace extends AppCompatActivity
//        implements OnMapReadyCallback {
//
//    private static final String TAG = MapsActivityCurrentPlace.class.getSimpleName();
//    private GoogleMap map;
//    private CameraPosition cameraPosition;
//
//    // The entry point to the Places API.
//    private PlacesClient placesClient;
//
//    // The entry point to the Fused Location Provider.
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
//    // A default location (Sydney, Australia) and default zoom to use when location permission is
//    // not granted.
//    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
//    private static final int DEFAULT_ZOOM = 15;
//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//    private boolean locationPermissionGranted;
//
//    // The geographical location where the device is currently located. That is, the last-known
//    // location retrieved by the Fused Location Provider.
//    private Location lastKnownLocation;
//
//    // Keys for storing activity state.
//    // [START maps_current_place_state_keys]
//    private static final String KEY_CAMERA_POSITION = "camera_position";
//    private static final String KEY_LOCATION = "location";
//    // [END maps_current_place_state_keys]
//
//    // Used for selecting the current place.
//    private static final int M_MAX_ENTRIES = 5;
//    private String[] likelyPlaceNames;
//    private String[] likelyPlaceAddresses;
//    private List[] likelyPlaceAttributions;
//    private LatLng[] likelyPlaceLatLngs;
//
//    // [START maps_current_place_on_create]
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // [START_EXCLUDE silent]
//        // [START maps_current_place_on_create_save_instance_state]
//        // Retrieve location and camera position from saved instance state.
//        if (savedInstanceState != null) {
//            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
//        }
//        // [END maps_current_place_on_create_save_instance_state]
//        // [END_EXCLUDE]
//
//        // Retrieve the content view that renders the map.
//        setContentView(R.layout.activity_maps);
//
//        // [START_EXCLUDE silent]
//        // Construct a PlacesClient
//        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
//        placesClient = Places.createClient(this);
//
//        // Construct a FusedLocationProviderClient.
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        // Build the map.
//        // [START maps_current_place_map_fragment]
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.db371d029c5a76e3);
//        mapFragment.getMapAsync(this);
//        // [END maps_current_place_map_fragment]
//        // [END_EXCLUDE]
//    }
//    // [END maps_current_place_on_create]
//
//    /**
//     * Saves the state of the map when the activity is paused.
//     */
//    // [START maps_current_place_on_save_instance_state]
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        if (map != null) {
//            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
//            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
//        }
//        super.onSaveInstanceState(outState);
//    }
//    // [END maps_current_place_on_save_instance_state]
//
//    /**
//     * Sets up the options menu.
//     * @param menu The options menu.
//     * @return Boolean.
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.current_place_menu, menu);
//        return true;
//    }
//
//    /**
//     * Handles a click on the menu option to get a place.
//     * @param item The menu item to handle.
//     * @return Boolean.
//     */
//    // [START maps_current_place_on_options_item_selected]
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.option_get_place) {
//            showCurrentPlace();
//        }
//        return true;
//    }
//    // [END maps_current_place_on_options_item_selected]
//
//    /**
//     * Manipulates the map when it's available.
//     * This callback is triggered when the map is ready to be used.
//     */
//    // [START maps_current_place_on_map_ready]
//    @Override
//    public void onMapReady(GoogleMap map) {
//        this.map = map;
//
//        // [START_EXCLUDE]
//        // [START map_current_place_set_info_window_adapter]
//        // Use a custom info window adapter to handle multiple lines of text in the
//        // info window contents.
//        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
//                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
//                        (FrameLayout) findViewById(R.id.db371d029c5a76e3), false);
//
//                TextView title = infoWindow.findViewById(R.id.title);
//                title.setText(marker.getTitle());
//
//                TextView snippet = infoWindow.findViewById(R.id.snippet);
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });
//        // [END map_current_place_set_info_window_adapter]
//
//        // Prompt the user for permission.
//        getLocationPermission();
//        // [END_EXCLUDE]
//
//        // Turn on the My Location layer and the related control on the map.
//        updateLocationUI();
//
//        // Get the current location of the device and set the position of the map.
//        getDeviceLocation();
//    }
//    // [END maps_current_place_on_map_ready]
//
//    /**
//     * Gets the current location of the device, and positions the map's camera.
//     */
//    // [START maps_current_place_get_device_location]
//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (locationPermissionGranted) {
//                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            lastKnownLocation = task.getResult();
//                            if (lastKnownLocation != null) {
//                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(lastKnownLocation.getLatitude(),
//                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            }
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            map.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
//                            map.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage(), e);
//        }
//    }
//    // [END maps_current_place_get_device_location]
//
//    /**
//     * Prompts the user for permission to use the device location.
//     */
//    // [START maps_current_place_location_permission]
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//    // [END maps_current_place_location_permission]
//
//    /**
//     * Handles the result of the request for location permissions.
//     */
//    // [START maps_current_place_on_request_permissions_result]
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        locationPermissionGranted = false;
//        if (requestCode
//                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationPermissionGranted = true;
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//        updateLocationUI();
//    }
//    // [END maps_current_place_on_request_permissions_result]
//
//    /**
//     * Prompts the user to select the current place from a list of likely places, and shows the
//     * current place on the map - provided the user has granted location permission.
//     */
//    // [START maps_current_place_show_current_place]
//    private void showCurrentPlace() {
//        if (map == null) {
//            return;
//        }
//
//        if (locationPermissionGranted) {
//            // Use fields to define the data types to return.
//            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
//                    Place.Field.LAT_LNG);
//
//            // Use the builder to create a FindCurrentPlaceRequest.
//            FindCurrentPlaceRequest request =
//                    FindCurrentPlaceRequest.newInstance(placeFields);
//
//            // Get the likely places - that is, the businesses and other points of interest that
//            // are the best match for the device's current location.
//            @SuppressWarnings("MissingPermission") final
//            Task<FindCurrentPlaceResponse> placeResult =
//                    placesClient.findCurrentPlace(request);
//            placeResult.addOnCompleteListener (new OnCompleteListener<FindCurrentPlaceResponse>() {
//                @Override
//                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        FindCurrentPlaceResponse likelyPlaces = task.getResult();
//
//                        // Set the count, handling cases where less than 5 entries are returned.
//                        int count;
//                        if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
//                            count = likelyPlaces.getPlaceLikelihoods().size();
//                        } else {
//                            count = M_MAX_ENTRIES;
//                        }
//
//                        int i = 0;
//                        likelyPlaceNames = new String[count];
//                        likelyPlaceAddresses = new String[count];
//                        likelyPlaceAttributions = new List[count];
//                        likelyPlaceLatLngs = new LatLng[count];
//
//                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
//                            // Build a list of likely places to show the user.
//                            likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
//                            likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
//                            likelyPlaceAttributions[i] = placeLikelihood.getPlace()
//                                    .getAttributions();
//                            likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
//
//                            i++;
//                            if (i > (count - 1)) {
//                                break;
//                            }
//                        }
//
//                        // Show a dialog offering the user the list of likely places, and add a
//                        // marker at the selected place.
//                        MapsActivityCurrentPlace.this.openPlacesDialog();
//                    }
//                    else {
//                        Log.e(TAG, "Exception: %s", task.getException());
//                    }
//                }
//            });
//        } else {
//            // The user has not granted permission.
//            Log.i(TAG, "The user did not grant location permission.");
//
//            // Add a default marker, because the user hasn't selected a place.
//            map.addMarker(new MarkerOptions()
//                    .title(getString(R.string.default_info_title))
//                    .position(defaultLocation)
//                    .snippet(getString(R.string.default_info_snippet)));
//
//            // Prompt the user for permission.
//            getLocationPermission();
//        }
//    }
//    // [END maps_current_place_show_current_place]
//
//    /**
//     * Displays a form allowing the user to select a place from a list of likely places.
//     */
//    // [START maps_current_place_open_places_dialog]
//    private void openPlacesDialog() {
//        // Ask the user to choose the place where they are now.
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // The "which" argument contains the position of the selected item.
//                LatLng markerLatLng = likelyPlaceLatLngs[which];
//                String markerSnippet = likelyPlaceAddresses[which];
//                if (likelyPlaceAttributions[which] != null) {
//                    markerSnippet = markerSnippet + "\n" + likelyPlaceAttributions[which];
//                }
//
//                // Add a marker for the selected place, with an info window
//                // showing information about that place.
//                map.addMarker(new MarkerOptions()
//                        .title(likelyPlaceNames[which])
//                        .position(markerLatLng)
//                        .snippet(markerSnippet));
//
//                // Position the map's camera at the location of the marker.
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
//                        DEFAULT_ZOOM));
//            }
//        };
//
//        // Display the dialog.
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(R.string.pick_place)
//                .setItems(likelyPlaceNames, listener)
//                .show();
//    }
//    // [END maps_current_place_open_places_dialog]
//
//    /**
//     * Updates the map's UI settings based on whether the user has granted location permission.
//     */
//    // [START maps_current_place_update_location_ui]
//    private void updateLocationUI() {
//        if (map == null) {
//            return;
//        }
//        try {
//            if (locationPermissionGranted) {
//                map.setMyLocationEnabled(true);
//                map.getUiSettings().setMyLocationButtonEnabled(true);
//            } else {
//                map.setMyLocationEnabled(false);
//                map.getUiSettings().setMyLocationButtonEnabled(false);
//                lastKnownLocation = null;
//                getLocationPermission();
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }
//    // [END maps_current_place_update_location_ui]
//}

//public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
//            LocationListener{
//
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//    private static final int RC_SIGN_IN = 9001;
////    private GoogleSignInClient mSignInClient; //mGoogleApiClient;
//    Location mLastLocation;
//    Marker mCurrLocationMarker;
//    LocationRequest mLocationRequest;
//    protected LocationManager locationManager;
//    protected LocationListener locationListener;
//    protected Context context;
//    TextView txtLat;
//    String lat;
//    String provider;
////    protected String latitude,longitude;
//    protected boolean gps_enabled,network_enabled;
//
//    private GoogleMap mMap;
//    private MapView mMapView;
//    double latitude;
//    double longitude;
//
//    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
//    private ActivityMapsBinding binding; // ex code doesn't have it?
//    private ContextCompat ActivityCompat;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }
////        GoogleMapOptions options = new GoogleMapOptions().mapId("db371d029c5a76e3");
////        SupportMapFragment mapFragment = SupportMapFragment.newInstance(options);
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager()
//                        .findFragmentById(R.id.db371d029c5a76e3);
////        MapView mapView = new MapView(context, options);
//
//        mapFragment.getMapAsync(this);
//        txtLat = (TextView) findViewById(R.id.textview1);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//
//        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        final double longitude = location.getLongitude();
//        final double latitude = location.getLatitude();
//
////        final LocationListener locationListener = new LocationListener() {
////            public void onLocationChanged(Location location) {
////                longitude = location.getLongitude();
////                latitude = location.getLatitude();
////            }
////        };
//
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
//    }
//
//
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
//        mMap.getUiSettings().setCompassEnabled(true);
//        //Initialize Google Play Services
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
////                buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);
//            }
//        } else {
//            mMap.setMyLocationEnabled(true);
//        }
//
//        // Add a marker in Sydney and move the camera
//        LatLng boston = new LatLng(42.35, -72.1);
//        LatLng current = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(boston).title("Marker in Boston"));
//        mMap.addMarker(new MarkerOptions().position(current).title("Marker current"));
//        mMap.moveCamera(newLatLngZoom(boston,14.5f)); // zoom value between 1~20. 1 is world, 5 is landmass, 20 is buildings
//        mMap.moveCamera(newLatLngZoom(current,14.5f));
//    }
//
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }
//        //Showing Current Location Marker on Map
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        LocationManager locationManager = (LocationManager)
//                getSystemService(Context.LOCATION_SERVICE);
//        String provider = locationManager.getBestProvider(new Criteria(), true);
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Location locations = locationManager.getLastKnownLocation(provider);
//        List<String> providerList = locationManager.getAllProviders();
//        if (null != locations && null != providerList && providerList.size() > 0) {
//            double longitude = locations.getLongitude();
//            double latitude = locations.getLatitude();
//            Geocoder geocoder = new Geocoder(getApplicationContext(),
//                    Locale.getDefault());
//            try {
//                List<Address> listAddresses = geocoder.getFromLocation(latitude,
//                        longitude, 1);
//                if (null != listAddresses && listAddresses.size() > 0) {
//                    String state = listAddresses.get(0).getAdminArea();
//                    String country = listAddresses.get(0).getCountryName();
//                    String subLocality = listAddresses.get(0).getSubLocality();
//                    markerOptions.title("" + latLng + "," + subLocality + "," + state
//                            + "," + country);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
////        if (mGoogleApiClient != null) {
////            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
////                    this);
////        }
//       }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        Log.d("Latitude","disable");
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        Log.d("Latitude","enable");
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        Log.d("Latitude","status");
//    }
//
//
////    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//    }
//    public boolean checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
////            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
////                    Manifest.permission.ACCESS_FINE_LOCATION)) {
////                ActivityCompat.requestPermissions(this,
////                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
////                        MY_PERMISSIONS_REQUEST_LOCATION);
////            } else {
////                ActivityCompat.requestPermissions(this,
////                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
////                        MY_PERMISSIONS_REQUEST_LOCATION);
////            }
//            return false;
//        } else {
//            return true;
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
////                        if (mGoogleApiClient == null) {
////                            buildGoogleApiClient();
////                        }
//                        mMap.setMyLocationEnabled(true);
//                    }
//                } else {
//                    Toast.makeText(this, "permission denied",
//                            Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//        }
//    }
//
//
//}


//package com.example.ec327_mapfunc;
//
//        import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;
//
//        import androidx.fragment.app.FragmentActivity;
//        import androidx.appcompat.app.AppCompatActivity;
//        import android.os.Bundle;
//        import com.google.android.gms.maps.CameraUpdateFactory;
//        import com.google.android.gms.maps.GoogleMap;
//        import com.google.android.gms.maps.GoogleMapOptions;
//        import com.google.android.gms.maps.MapView;
//        import com.google.android.gms.maps.OnMapReadyCallback;
//        import com.google.android.gms.maps.SupportMapFragment;
//        import com.google.android.gms.maps.model.LatLng;
//        import com.google.android.gms.maps.model.MarkerOptions;
//        import com.example.ec327_mapfunc.databinding.ActivityMapsBinding;
//        import android.util.Log;
//        import android.content.Context;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {
//
//    private GoogleMap mMap;
//    private MapView mMapView;
//    Context context;
//    double lat;
//    double longi;
//    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
//    private ActivityMapsBinding binding; // ex code doesn't have it?
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        binding = ActivityMapsBinding.inflate(getLayoutInflater()); // ex code doesn't have it?
////        setContentView(binding.getRoot());
//        // Retrieve the content view that renders the map.
//        setContentView(R.layout.activity_maps);
//
////        GoogleMapOptions options = new GoogleMapOptions().mapId("db371d029c5a76e3");
////        SupportMapFragment mapFragment = SupportMapFragment.newInstance(options);
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager()
//                        .findFragmentById(R.id.db371d029c5a76e3);
////        MapView mapView = new MapView(context, options);
////        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
////                .findFragmentById(R.id."db371d029c5a76e3");
//        mapFragment.getMapAsync(this);
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng boston = new LatLng(42.35, -71.1);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.addMarker(new MarkerOptions().position(boston).title("Marker in Boston"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.moveCamera(newLatLngZoom(boston,14.5f)); // zoom value between 1~20. 1 is world, 5 is landmass, 20 is buildings
//    }
