package com.example.cr7.tesse;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapPickerActivity extends AppCompatActivity {
    //Khai báo đối tượng Google Map
    GoogleMap mMap;
    public static int PERMISSION_ALL = 1;
    ArrayList<LatLng> arrPosition = new ArrayList<>();
    Polyline line;
    private FusedLocationProviderClient mFusedLocationClient;
    LatLng mylocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker);
        getSupportActionBar().hide();
        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (!hasPermissions(MapPickerActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        initViews();
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void initViews() {
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id
                        .fragment_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        if (ActivityCompat.checkSelfPermission(MapPickerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapPickerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MapPickerActivity.this);
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(MapPickerActivity.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            // Logic to handle location object
                                            mylocation = new LatLng(location.getLatitude(), location.getLongitude());

                                        } else {
                                            mylocation = new LatLng(10.848501, 106.786544);
                                        }
                                        mMap.addMarker(new MarkerOptions()
                                                .position(mylocation)
                                                .title("Marker in MyLocation")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.myloca))
                                        );
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15));
                                        Log.e("Last Know location: ", location.getLatitude() + " " + location.getLongitude());
                                        ContainerActivity.LAT = mylocation.latitude;
                                        ContainerActivity.LON = mylocation.longitude;

                                    }
                                });

                    }
                });



                if (ActivityCompat.checkSelfPermission(MapPickerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapPickerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        Toast.makeText(MapPickerActivity.this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                        // Return false so that we don't consume the event and the default behavior still occurs
                        // (the camera animates to the user's current position).
                        return true;

                    }
                });

                addEvent();

            }
        });
    }

    private void addEvent() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.myloca));
                arrPosition.add(latLng);
                mMap.addMarker(markerOptions);
                Log.e("Location ", latLng.latitude + " " + latLng.longitude);
                Toast.makeText(MapPickerActivity.this, "Your Position: "+ latLng.latitude + " " + latLng.longitude, Toast.LENGTH_SHORT).show();
                ContainerActivity.LAT = latLng.latitude;
                ContainerActivity.LON = latLng.longitude;

            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MapPickerActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MapPickerActivity.this);
        }
        builder.setTitle("Your exactly location.")
                .setMessage("Are you sure done set it as your location? \n "+ ContainerActivity.LAT +","+ContainerActivity.LON )
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
