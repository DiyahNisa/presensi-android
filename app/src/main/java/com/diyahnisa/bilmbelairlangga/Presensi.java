 package com.diyahnisa.bilmbelairlangga;

 import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
 import android.os.Build;
 import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
 import androidx.annotation.RequiresApi;
 import androidx.appcompat.app.AppCompatActivity;

import com.diyahnisa.bilmbelairlangga.ApiService.ApiClient;
import com.diyahnisa.bilmbelairlangga.Model.ResponPresensi;
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

    EditText editTanggal, editTime;
    String karyawan_kode, tglPresensi, waktuPresensi, ketPresensi, lokasiRuang;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    Button btnPresensi;
    public static final String BASE_URL = "http://192.168.0.104/airlanggaBimbel/public/api/";
//    public static final String BASE_URL = "https://bimbelairlangga.my.id/public/api/";
    SharedPreferences sharedPreferences;public
    Spinner spinner;
    private String [] lokasiR = {"Alpha.01","Alpha.02","Alpha.03","Alpha.04",
                                    "Betha.01","Betha.02","Betha.03","Betha.04" };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Presensi.this);
        editTanggal=(EditText)findViewById(R.id.editTanggal);
        karyawan_kode = sharedPreferences.getString("karyawan_kode", null);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, lokasiR);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                switch (i) {
                    case 0:
                        lokasiRuang = "Alpha.01";
                        break;
                    case 1:
                        lokasiRuang = "Alpha.02";
                        break;
                    case 3:
                        lokasiRuang = "Alpha.03";
                        break;
                    case 4:
                        lokasiRuang = "Alpha.04";
                        break;
                    case 5:
                        lokasiRuang = "Betha.01";
                        break;
                    case 6:
                        lokasiRuang = "Betha.02";
                        break;
                    case 7:
                        lokasiRuang ="Betha.03";
                        break;
                    case 8:
                        lokasiRuang = "Betha.04";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
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
        tglPresensi = editTanggal.getText().toString();
        waktuPresensi = editTime.getText().toString();
        ketPresensi = "Hadir";
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
            ApiClient presensiApi = retrofit.create(ApiClient.class);
            Call<ResponPresensi> call = presensiApi.addPresensi(karyawan_kode,tglPresensi,waktuPresensi,ketPresensi, lokasiRuang);
            call.enqueue(new Callback<ResponPresensi>() {
                @Override
                public void onResponse(Call<ResponPresensi> call, Response<ResponPresensi> response) {
                    String success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if (success != null) {
                        alertDialogBuilder.setTitle("Informasi");
                        alertDialogBuilder
                                .setMessage(message)
                                .setCancelable(false)
                                .setNeutralButton("Oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(Presensi.this,LogBook.class);
                                        intent.putExtra("karyawan_kode",karyawan_kode);
                                        startActivity(intent);
                                    }
                                });
                        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
//                        Toast.makeText(Presensi.this, "Tidak berhasil melakukan presensi", Toast.LENGTH_SHORT).show();
                        alertDialogBuilder.setTitle("Informasi");
                        alertDialogBuilder
                                .setMessage("Tidak berhasil melakukan presensi")
                                .setCancelable(false)
                                .setNeutralButton("Oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
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

    //Menu LogOut
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
