package com.example.android.test7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.R.attr.action;
import static com.example.android.test7.R.id.map;
import static com.example.android.test7.R.mipmap.ic_launcher;
import static com.example.android.test7.R.raw.crosswalk;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.widget.ZoomControls;
import android.location.Location;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.Toolbar;

import android.os.Vibrator;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings myUIset;
    private static final int MY_PERMISSION_FINE_LOCATION = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//        toolbar.setLogo(R.mipmap.ic_launcher);



    }



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
        mMap.setBuildingsEnabled(true);
        myUIset = mMap.getUiSettings();
        myUIset.setZoomControlsEnabled(true);
        myUIset.setCompassEnabled(true);
        myUIset.setAllGesturesEnabled(true);
        myUIset.setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        // Add a marker in Fountains Crosswalk and move the camera
        LatLng fountainsCW = new LatLng(30.266089, -81.504488);
        mMap.addMarker(new MarkerOptions().position(fountainsCW).title("Fountains Crosswalk"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fountainsCW,17.0f));

        //Test Alert Dialog Button
        final LatLng testLatLng = new LatLng(30.266089, -81.504488);

        //Alert Dialog builder
        final AlertDialog.Builder crosswalkAlert = new AlertDialog.Builder(this);
        crosswalkAlert.setMessage("Please do not attempt to cross. Here is the nearest crosswalk")
                .setPositiveButton("Nearest Marker", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(testLatLng, (float) 20.0f));
                        //Toast for testing
                        Toast.makeText(getApplicationContext(), "The nearest crosswalk is here", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                        .setTitle("Warning")
                        .create();


        //Apply Demorgan's Law
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        }





        //Polylines
        PolylineOptions line1 = new PolylineOptions();
        line1.add(new LatLng(30.26671498719688,-81.5046863257885),
                new LatLng(30.26618424827013,-81.50451198220253));
        line1.color(Color.RED);
        line1.width(45);
        line1.visible(false);
        Polyline lineTest3 = mMap.addPolyline((line1));
        lineTest3.setClickable(true);


        PolylineOptions line2 = new PolylineOptions();
        line2.add(new LatLng(30.266048286910607,-81.50446705520153),
                new LatLng(30.265927068190354,-81.50444157421589),
                new LatLng(30.265827144538132,-81.50442950427532),
                new LatLng(30.265655144570733,-81.50443084537983));
        line2.color(Color.RED);
        line2.width(45);
        line2.visible(false);
        Polyline lineTest4 = mMap.addPolyline((line2));
        lineTest4.setClickable(true);

        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener()
        {
            @Override
            public void onPolylineClick(Polyline polyline)
            {

                // Get instance of Vibrator from current Context
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 400 milliseconds
                vibe.vibrate(400);
                crosswalkAlert.show();

                //Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();

            }
        });

        //Comment this out - just for the video
        List<LatLng> crosswalkLatLngList = new ArrayList<LatLng>();
        String cwData = "";
        InputStream crosswalkIS;

         /* * * * * * * KEEP THIS UNTIL DB IS MADE * * * * * * * * * */
        //Gets the latitude and longitude of the crosswalks from native CSV File
        crosswalkIS=getResources().openRawResource(crosswalk);
        BufferedReader reader = new BufferedReader(new InputStreamReader(crosswalkIS));
        try {
            while((cwData = reader.readLine()) !=null){
                try{
                    double lat = Double.parseDouble(cwData.split(",")[0]);
                    double lon = Double.parseDouble(cwData.split(",")[1]);
                    crosswalkLatLngList.add(new LatLng(lat, lon));

                }catch(Exception e){
                    Log.e("Unknown", e.toString());

                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error: Cannot Read CSV File"+e);
        }

        for(LatLng pos : crosswalkLatLngList)
        {
            mMap.addMarker(new MarkerOptions()
                    .position(pos));

        }
        //UNTIL HERE



    }



    //Nearest Crosswalk Button
    public void nearestCrossWalk(View v) throws IOException {


        LatLng testLatLng = new LatLng(30.266089, -81.504488);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(testLatLng, (float) 20.0f));


        // Get instance of Vibrator from current Context
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        vibe.vibrate(400);

        //Toast for testing
        Toast.makeText(getApplicationContext(), "The nearest crosswalk is here", Toast.LENGTH_LONG).show();

    }

}
