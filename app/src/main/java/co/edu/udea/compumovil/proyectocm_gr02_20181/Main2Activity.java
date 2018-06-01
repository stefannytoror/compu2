package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, DirectionCallback {

    private Button btnRequestDirection , btnSaveRoute;
    private GoogleMap googleMap;
    private LatLng origin; // = new LatLng(37.7849569, -122.4068855);
    private LatLng destination; // = new LatLng(37.7814432, -122.4460177);
    private ArrayList<LatLng> listPoints;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private String from ;
    private String to;
    private String hour;
    private String UUID;
    private String date;
    private String name;

    private String serverKey = "AIzaSyDbuQvU2I4_emVOJ0vlP6flEqUQRDvl3Ig";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnRequestDirection = findViewById(R.id.btn_request_direction);
        btnRequestDirection.setOnClickListener(this);
        btnSaveRoute = findViewById(R.id.btn_saveRoute);
        btnSaveRoute.setOnClickListener(this);
        listPoints = new ArrayList<>();
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        Bundle b = getIntent().getExtras();
        from =b.getString("from");
        to =b.getString("to");
        hour = b.getString("hour");
        date = b.getString("date");
        UUID = b.getString("UUID");

        Log.d("TAG", "onMapReady: " + b);
        Log.d("TAG", "onMapReady: "+ from);

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if (listPoints.size() == 2){
                    listPoints.clear();
                    googleMap.clear();
                }

                // guardar el primer punto
                listPoints.add(latLng);
                //crear la marca
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if (listPoints.size()==1){
                    //agregar marca al mapa
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    origin = latLng;
                }
                else{
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
                googleMap.addMarker(markerOptions);
                if (listPoints.size()==2){
                    origin = listPoints.get(0);
                    destination = listPoints.get(1);
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_request_direction) {
            requestDirection();

            // save info

        }
        else if(id==R.id.btn_saveRoute){
            Bundle b = getIntent().getExtras();
            from =b.getString("from");
            to =b.getString("to");
            hour = b.getString("hour");
            date = b.getString("date");
            UUID = b.getString("UUID");
            name = b.getString("name");


            Log.d("TAG", "onMapReady: " + b);
            Log.d("TAG", "onMapReady: "+ from);
            Event event = new Event(UUID,from,to,name,hour,date,origin.toString(),destination.toString());
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference();
            mDatabaseReference.child("events").child(event.getUid()).setValue(event);

            Intent intent = new Intent(getApplicationContext(), NDActivity.class);

            startActivity(intent);



        }
    }

    public void requestDirection() {
        Log.d("", "requestDirection: origen" + origin);
        Log.d("", "requestDirection: origen" + destination);
        Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.WALKING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            googleMap.addMarker(new MarkerOptions().position(origin));
            googleMap.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED));
            setCameraWithCoordinationBounds(route);

            //btnRequestDirection.setVisibility(View.GONE);
        } else {
            Snackbar.make(btnRequestDirection, direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(btnRequestDirection, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
