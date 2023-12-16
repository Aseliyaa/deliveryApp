package by.ruban.deliveryapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import by.ruban.deliveryapp.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private SearchView mapSearchView;
    private ImageView backBtn;
    private Marker mMarker;
    private ConstraintLayout changeBtn;
    private String selectedAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapSearchView = findViewById(R.id.mapSearch);
        backBtn = findViewById(R.id.backBtn);
        changeBtn = findViewById(R.id.changeBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, CartActivity.class));
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = "";
                if (mMarker != null) {
                    location = mMarker.getTitle();
                }
                if (!location.isEmpty()) {
                    selectedAddress = location;

                    Intent intent = new Intent(MapsActivity.this, CartActivity.class);
                    intent.putExtra("selectedAddress", selectedAddress);
                    startActivity(intent);
                } else {
                    Toast.makeText(MapsActivity.this, "Please select a location", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = mapSearchView.getQuery().toString();

                List<Address> addressList = null;
                if (mMarker != null) {
                    mMarker.remove();
                }
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        mapSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (mMarker != null) {
                    mMarker.remove();
                    mMarker = null;
                }
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}