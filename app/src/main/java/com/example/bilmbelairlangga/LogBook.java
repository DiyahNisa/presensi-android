package com.example.bilmbelairlangga;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class LogBook extends AppCompatActivity {

    EditText editTanggal2, editTime2, editTime3;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    Button btnLogBook, btnLihatLog;
    public static final String URL = "http://192.168.0.104/airlanggaBimbel/public/api/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);

        editTanggal2=(EditText)findViewById(R.id.editTanggal2);
        dateFormat = new SimpleDateFormat("dd-MM-yyy");

        editTanggal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(LogBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,month,dayOfMonth);
                        editTanggal2.setText(dateFormat.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        editTime2=(EditText)findViewById(R.id.editTime2);
        editTime3=(EditText)findViewById(R.id.editTime3);

        editTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar= Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int mins=calendar.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(LogBook.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                TimePickerDialog timePickerDialog = new TimePickerDialog(LogBook.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        editTime2.setText(time);
                    }
                },hours ,mins, false);
                timePickerDialog.show();
            }
        });

        editTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int mins=calendar.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(LogBook.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                TimePickerDialog timePickerDialog = new TimePickerDialog(LogBook.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        editTime3.setText(time);
                    }
                },hours ,mins, false);
                timePickerDialog.show();
            }
        });

        btnLihatLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogBook.this, LogBookView.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_logbook);
        bottomNavigationView.setSelectedItemId(R.id.nav_logBook);
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
                        startActivity(new Intent(getApplicationContext()
                                , Presensi.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.nav_logBook:
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

// Menu LogOut
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
                                startActivity(new Intent(LogBook.this, Login.class));
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
