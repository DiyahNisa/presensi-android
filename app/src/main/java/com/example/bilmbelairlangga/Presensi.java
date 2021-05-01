package com.example.bilmbelairlangga;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bilmbelairlangga.ApiService.PresensiApi;
import com.example.bilmbelairlangga.Model.ResponPresensi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presensi extends AppCompatActivity {

    TextView textName;
    EditText editTanggal, editTime;
    String tglPresensi, waktuPresensi;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    Button btnPresensi;
    public static final String BASE_URL = "http://192.168.0.104/airlanggaBimbel/public/api/";
    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Presensi.this);
//        String namaUser = sharedPreferences.getString(Login.KEY_USER, null);

        textName = (TextView) findViewById(R.id.textName);
        editTanggal=(EditText)findViewById(R.id.editTanggal);

        dateFormat = new SimpleDateFormat("dd-MM-yyy");
        editTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(Presensi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,month,dayOfMonth);
                        editTanggal.setText(dateFormat.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();;
            }
        });

        editTime=(EditText)findViewById(R.id.editTime);
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int mins=calendar.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(Presensi.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Presensi.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        editTime.setText(time);
                    }
                },hours ,mins, false);
                timePickerDialog.show();
            }
        });

        btnPresensi = (Button) findViewById(R.id.btnPresensi);
        btnPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimPresensi();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_presensi);
        bottomNavigationView.setSelectedItemId(R.id.nav_presensi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                        , MainActivity.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.nav_presensi:
                        return false;
                    case R.id.nav_logBook:
                        startActivity(new Intent(getApplicationContext()
                                , LogBook.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.nav_izin:
                        startActivity(new Intent(getApplicationContext()
                                , Izin.class));
                        overridePendingTransition(0,0);
                        return false;
                }
                return false;
            }
        });
    }

    private void kirimPresensi() {
        //ambil data dari editText
        Log.d("editTanggal",tglPresensi = editTanggal.getText().toString());
        Log.d("waktuPresensi",waktuPresensi = editTime.getText().toString());
        if (tglPresensi.isEmpty()) {
            editTanggal.setError("Masukkan Tanggal");
        } else if (waktuPresensi.isEmpty()) {
            editTime.setError("Masukkan Waktu");
        } else {
            //membuat pesan dialog
            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
            ProgressDialog progressDialog = new ProgressDialog(Presensi.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Tunggu sebentar...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PresensiApi presensiApi = retrofit.create(PresensiApi.class);
            Call<ResponPresensi> call = presensiApi.addPresensi(tglPresensi, waktuPresensi);
            call.enqueue(new Callback<ResponPresensi>() {
                @Override
                public void onResponse(Call<ResponPresensi> call, Response<ResponPresensi> response) {

                    String success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    Log.d("RETRO", "Respon : " + message);

                    Toast.makeText(Presensi.this, success, Toast.LENGTH_SHORT).show();

//                    if (success == "Ok") {
//                        Toast.makeText(Presensi.this, message, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(Presensi.this, "Tidak berhasil melakukan presensi", Toast.LENGTH_SHORT).show();
//                    }
                }

                @Override
                public void onFailure(Call<ResponPresensi> call, Throwable t) {
                    progressDialog.dismiss();
                    alertDialogBuilder.setTitle("Informasi");
                    alertDialogBuilder
                            .setMessage("Gagal menghubungkan ke server")
                            .setCancelable(false)
                            .setNeutralButton("Oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.nav_logOut: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                alertDialogBuilder.setTitle("Konfirmasi");
                alertDialogBuilder
                        .setMessage("Apakah anda yakin ingin keluar ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                startActivity(new Intent(Presensi.this, Login.class));
                                finish();
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
