package com.duarbd.duarhcentralhub.presenter;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.databinding.ActivityMapsBinding;
import com.duarbd.duarhcentralhub.tools.Utils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int SEARCH_LOCATION_REQUEST_CODE = 111;
    private static final float DEFAULT_ZOOM = 17f;

    private static final String TAG = "MapsActivity";
    private ActivityMapsBinding binding;
    private GoogleMap mMap;


    private Marker markerAddress;
    private PlacesClient placesClient;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initPlacesAPI();

        binding.searchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAutoComplete(SEARCH_LOCATION_REQUEST_CODE);
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moveCamera(new LatLng(25.734121, 89.287155),"onMapReady");

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                markerAddress.setPosition(marker.getPosition());
            }
        });
    }

    private void initPlacesAPI() {
        try {
            if (!Places.isInitialized()) {
                //Places.initialize(getApplicationContext(), "AIzaSyCdP8QSuapjIn5DZEfWXG5EH6EIiYb6uuY");//this key belongs to Arif Rayn
                Places.initialize(getApplicationContext(), "AIzaSyCWzXk-SGxgedHpO8pnTA0h6aYzEiJL_ss"); //this key belongs to Takveer
            }
            placesClient = Places.createClient(this);
        } catch (Exception e) {
            Log.d(TAG, "initPlacesAPI: error="+e.getMessage());
        }
    }

    private void initAutoComplete(int CALLER_REQUEST_CODE) {

        initPlacesAPI();

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG,
                Place.Field.TYPES);

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("bd")

                .build(this);
        startActivityForResult(intent, CALLER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==SEARCH_LOCATION_REQUEST_CODE){
            switch (resultCode) {
                case RESULT_OK:
                    place = Autocomplete.getPlaceFromIntent(data);
                    Log.d(TAG, "onActivityResult: place name="+place.getName()+" "+"Address="+place.getAddress());
                    Log.d(TAG, "onActivityResult: Utils.getAddress() ="+
                            Utils.getAddress(getApplicationContext(),place.getLatLng().latitude,place.getLatLng().longitude));
                    if (markerAddress == null) {
                        markerAddress = mMap.addMarker(new MarkerOptions()
                                .position(place.getLatLng())
                                .draggable(true)
                                .icon(getBitmapDescriptor(getResources().getDrawable(R.drawable.ic_location, null)))
                                .title(place.getName()));
                    } else {
                        markerAddress.setTitle(place.getName());
                        markerAddress.setPosition(place.getLatLng());
                    }
                    markerAddress.showInfoWindow();
                    moveCamera(markerAddress.getPosition(),18f);
                    ActivityRegisterNewClient.clientBusinessPlace=place;
                    break;
                case AutocompleteActivity.RESULT_ERROR:
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.d(TAG, "onActivityResult:RESULT_ERROR: " + status.getStatusMessage());
                    break;
                case RESULT_CANCELED:
                    Log.d(TAG, "onActivityResult: Place search canceled");
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void moveCamera(Location location, String caller) {
        Log.d(TAG, "moveCamera: called by " + caller);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
    }

    public void moveCamera(LatLng latLng, String caller) {
        Log.d(TAG, "moveCamera: called by " + caller);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    public void moveCamera(LatLng latLng, float zoomlevel) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomlevel));
    }

    private BitmapDescriptor getBitmapDescriptor(Drawable vectorDrawable) {

        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bm = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bm);
    }
}