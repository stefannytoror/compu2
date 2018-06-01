package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionCallback {

    private GoogleMap googleMap2;
    private LatLng origin; // = new LatLng(37.7849569, -122.4068855);
    private LatLng destination; // = new LatLng(37.7814432, -122.4460177);
    private String serverKey = "AIzaSyDbuQvU2I4_emVOJ0vlP6flEqUQRDvl3Ig";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle b = getIntent().getExtras();
        Log.d("TAG", "onCreate: " + b);
        String[] corte1 = b.getString("origin").split("\\(");
        corte1 = corte1[1].split("\\)");

        String[] latlong1 = corte1[0].split(",");
        double latitud = Double.parseDouble(latlong1[0]);
        double longitud = Double.parseDouble(latlong1[1]);
        origin = new LatLng(latitud,longitud);


        corte1 = b.getString("destination").split("\\(");
        corte1 = corte1[1].split("\\)");


        String[] latlong2 = corte1[0].split(",");
        latitud = Double.parseDouble(latlong1[0]);
        longitud = Double.parseDouble(latlong1[1]);
        destination = new LatLng(latitud,longitud);


        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2)).getMapAsync(this);
        requestDirection();
    }

    public void requestDirection() {

        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.WALKING)
                .execute(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap2) {
        this.googleMap2 = googleMap2;
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            googleMap2.addMarker(new MarkerOptions().position(origin));
            googleMap2.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            googleMap2.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED));
            setCameraWithCoordinationBounds(route);

            //btnRequestDirection.setVisibility(View.GONE);
        } else {

        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }
    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap2.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }


}
