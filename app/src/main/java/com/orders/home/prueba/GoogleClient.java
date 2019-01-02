package com.orders.home.prueba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class GoogleClient extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private static final String TAG = GoogleClient.class.getName();
    private static final int INTERVAL = 5000;
    private static final int FAST_INTERVAL = 1000;
    boolean RLU;
    GoogleApiClient GAC;
    LocationRequest LR;
    TextView mLatitud;
    TextView mLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_client);
        mLatitud = (TextView) findViewById(R.id.latitud);
        mLongitud = (TextView) findViewById(R.id.longitud);
        Log.d(TAG, "onCreate: AAAAAAAA mi pichula");
        initGoogleApiClient();

        createLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (GAC != null) {
            if (GAC.isConnected()) {
                startLocationUpdates();
            } else {
                GAC.connect();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopLocationUpdates();
        super.onDestroy();
    }


    private void startLocationUpdates() {
        if (GAC != null && GAC.isConnected() && !RLU) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "startLocationUpdates: Actualizaciones de ubicación iniciadas");
                LocationServices.FusedLocationApi.requestLocationUpdates(GAC, LR, this);
                RLU = true;
            }
        }

    }

    private void stopLocationUpdates() {
        if (GAC != null && GAC.isConnected() && !RLU) {
            LocationServices.FusedLocationApi.removeLocationUpdates(GAC,this);
            GAC.disconnect();
            RLU = false;
        }
    }

    private void createLocationRequest() {
        LR = new LocationRequest();
        LR.setInterval(INTERVAL);
        LR.setFastestInterval(FAST_INTERVAL);
        LR.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void initGoogleApiClient() {
        if (GAC == null){
            Log.d(TAG, "initGoogleApiClient: Construcción correcta ");
            GAC = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: ");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: ");
        refreshUi(location);
    }

    private void refreshUi(Location location) {
        if(location!=null){
            mLatitud.setText(String.valueOf(location.getLatitude()));
            mLongitud.setText(String.valueOf(location.getLongitude()));
        }
    }
}
