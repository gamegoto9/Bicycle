package com.devdrunk.bicycle.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.devdrunk.bicycle.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    private ImageButton btnRequestDirection;
    private String serverKey = "AIzaSyBs27nAj-61dBENpuYORsgqci0Ljw67GTA";
    private LatLng camera = new LatLng(37.782437, -122.4281893);
    private LatLng origin;
    private LatLng destination = new LatLng(19.97742247, 99.85934973);

    LocationListener listener;
    LocationManager lm;
    double lat, lng;
    LatLng coordinate;
    boolean isNetwork;
    boolean isGPS;
    Marker mMarker;

    private static final int PLACE_PICKER_REQUEST = 1;

    public MapFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here


    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {

        btnRequestDirection = (ImageButton) rootView.findViewById(R.id.btn_search);
        btnRequestDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if(id == R.id.btn_search){


                    requestDirection();
                }
            }
        });




        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listener = new LocationListener() {
            public void onLocationChanged(Location loc) {
                coordinate = new LatLng(loc.getLatitude()
                        , loc.getLongitude());
                lat = loc.getLatitude();
                lng = loc.getLongitude();

                origin = new LatLng(lat, lng);


                Toast.makeText(getContext(),"Lat : "+lat + "Lng : "+lng ,
                        Toast.LENGTH_LONG).show();

                if(mMarker != null)
                    mMarker.remove();

                mMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        coordinate, 16));
            }

            public void onStatusChanged(String provider, int status
                    , Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };







    }


        public void requestDirection() {
            //Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
            GoogleDirection.withServerKey(serverKey)
                    .from(origin)
                    .to(destination)
                    .transportMode(TransportMode.DRIVING)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            //Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
                            if (direction.isOK()) {
                                mMap.addMarker(new MarkerOptions().position(origin));
                                mMap.addMarker(new MarkerOptions().position(destination));

                                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                                mMap.addPolyline(DirectionConverter.createPolyline(getContext(), directionPositionList, 5, Color.RED));

                                btnRequestDirection.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            Snackbar.make(btnRequestDirection, t.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });
        }



    private void checkLocationPermission() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        lm = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);

        isNetwork =
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPS =
                lm.isProviderEnabled(LocationManager.GPS_PROVIDER);


    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here

        outState.putParcelable("start",origin);
        outState.putParcelable("end",destination);
        outState.putString("keyMap",serverKey);



    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        origin = savedInstanceState.getParcelable("start");
        destination = savedInstanceState.getParcelable("end");
        serverKey = savedInstanceState.getString("keyMap");

        requestDirection();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        UiSettings uis = mMap.getUiSettings();
        uis.setZoomControlsEnabled(true);
        uis.setZoomGesturesEnabled(true);
        uis.setRotateGesturesEnabled(true);
        uis.setCompassEnabled(true);
        uis.setTiltGesturesEnabled(false);
        uis.setScrollGesturesEnabled(true);




        // Add a marker in Sydney and move the camera


       // origin = new LatLng(lat, lng);
//                        .position(new LatLng(lat, lng)));
       // LatLng sydney = origin;
        //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("My location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);


                if(isNetwork) {
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                            , 5000, 10, listener);
                    Location loc = lm.getLastKnownLocation(
                            LocationManager.NETWORK_PROVIDER);
                    if(loc != null) {
                        lat = loc.getLatitude();
                        lng = loc.getLongitude();
                    }
                }

                if(isGPS) {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER
                            , 5000, 10, listener);
                    Location loc = lm.getLastKnownLocation(
                            LocationManager.GPS_PROVIDER);
                    if(loc != null) {
                        lat = loc.getLatitude();
                        lng = loc.getLongitude();
                    }
                }


            }
        } else {
            //Not in api-23, no need to prompt
            mMap.setMyLocationEnabled(true);
        }

    }


}
