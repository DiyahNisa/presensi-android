package com.example.bilmbelairlangga;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSIONS_REQUEST = 99; //integer bebas, tapi maks 1 byte
    boolean ijin = false; //sudah mendapat ijin untuk mengakses Lokasi
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    SharedPreferences sharedPreferences;
    TextView detailUsername;
    TextView mLatText;
    TextView mLongText;
    String karyawan_id;

    @Override
    protected void onStart() {
        super.onStart();
        if (ijin) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (ijin) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailUsername = (TextView) findViewById(R.id.detailUsername);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        karyawan_id = sharedPreferences.getString("karyawan_id", null);
        String namaUser = sharedPreferences.getString(Login.KEY_USER, null);
        Snackbar snackbar = Snackbar.make(detailUsername,namaUser, Snackbar.LENGTH_LONG);
        snackbar.setAction("SELANJUTNYA", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Presensi.class);
                intent.putExtra("karyawan_id", karyawan_id);
                startActivity(intent);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
        Log.i("yw", "mulai!!");

        if (ContextCompat.checkSelfPermission(this,
                //hati2, jika konstanta tidak cocok, tidak ada runtimeerror
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
            // MY_PERMISSIONS_REQUEST adalah konstanta, nanti digunakan di  onRequestPermissionsResult

        } else {
            //sudah diijinkan
            ijin = true;
            buildGoogleApiClient();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_home);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        return false;
                    case R.id.nav_presensi:
                        startActivity(new Intent(getApplicationContext()
                                , Presensi.class));
                        overridePendingTransition(0, 0);
                        return false;
                    case R.id.nav_logBook:
                        startActivity(new Intent(getApplicationContext()
                                , LogBook.class));
                        overridePendingTransition(0, 0);
                        return false;
                    case R.id.nav_izin:
                        startActivity(new Intent(getApplicationContext()
                                , Izin.class));
                        overridePendingTransition(0, 0);
                        return false;
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //permission diberikan, mulai ambil Lokasi
                buildGoogleApiClient();
                ijin = true;
            } else {
                //permssion tidak diberikan, tampilkan pesan
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setMessage("Tidak mendapat ijin, tidak dapat mengambil lokasi");
                ad.show();
            }
            return;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("yw", "connected!!");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
//            mLatText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("yw", "suspend!!");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("yw", "failed!!");
    }
}