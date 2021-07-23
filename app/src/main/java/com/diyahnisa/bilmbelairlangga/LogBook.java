package com.diyahnisa.bilmbelairlangga;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.diyahnisa.bilmbelairlangga.ApiService.ApiClient;
import com.diyahnisa.bilmbelairlangga.Model.ResponLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogBook extends AppCompatActivity {

    ImageView viewBukti;
    EditText editTanggal2, editTime2, editTime3, editKetLog;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    Button btnLogBook, btnLihatLog, btnBukti, btnKamera;
    String mediaPath, postPath;
    SharedPreferences sharedPreferences;

    //Request User
//    public static final String BASE_URL = "http://192.168.0.104/airlanggaBimbel/public/api/";
        public static final String BASE_URL = "https://bimbelairlangga.my.id/public/api/";
    private  static final int IMAGE = 100;
    private  static final int KAMERA = 101;
    public static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, IMAGE);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LogBook.this);

        // Identifikasi Komponen Form
        viewBukti = (ImageView) findViewById(R.id.viewBukti);
        editKetLog =(EditText) findViewById(R.id.editKetLog);
        dateFormat = new SimpleDateFormat("dd-MM-yyy");

        editTanggal2=(EditText)findViewById(R.id.editTanggal2);
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

        editTime3=(EditText)findViewById(R.id.editTime3);
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

        btnKamera = (Button) findViewById(R.id.btnKamera);
        btnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera, KAMERA);
            }
        });

        btnBukti = (Button) findViewById(R.id.btnBukti);
        btnBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                } else {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, IMAGE);
                }
            }
        });

        btnLogBook= (Button) findViewById(R.id.btnLogBook);
        btnLogBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimLogBook();
            }
        });

        btnLihatLog = (Button) findViewById(R.id.btnLihatLog);
        btnLihatLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godata = new Intent(LogBook.this, LogBookView.class);
                godata.putExtra("karyawan_kode", sharedPreferences.getString("karyawan_kode",null));
                startActivity(godata);
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

//    private void requestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
//        } else {
//
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(galleryIntent, IMAGE);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE) {
                if (data != null) {
                    Uri selectedFile = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedFile, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    viewBukti.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            } else if(requestCode == KAMERA){

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                viewBukti.setImageBitmap(bitmap);
            }
        }

    }

    private void kirimLogBook() {
        if (mediaPath== null)
        {
            Toast.makeText(getApplicationContext(), "Form tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
        else {
            File imageFile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
            MultipartBody.Part buktiFoto = MultipartBody.Part.createFormData("buktiFoto", imageFile.getName(), reqBody);
//            Log.d("LIHAT", imageFile.getName());

            RequestBody karyawan_kode = RequestBody.create(MultipartBody.FORM, sharedPreferences.getString("karyawan_kode", null));
            RequestBody tglKegiatan = RequestBody.create(MultipartBody.FORM, editTanggal2.getText().toString());
            RequestBody waktuMulai = RequestBody.create(MultipartBody.FORM, editTime2.getText().toString());
            RequestBody waktuSelesai = RequestBody.create(MultipartBody.FORM, editTime3.getText().toString());
            RequestBody ketLog = RequestBody.create(MultipartBody.FORM, editKetLog.getText().toString());

            //membuat pesan dialog
            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
            ProgressDialog progressDialog = new ProgressDialog(LogBook.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Tunggu sebentar...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiClient logBookApi = retrofit.create(ApiClient.class);
            Call<ResponLog> call = logBookApi.addLog(karyawan_kode, tglKegiatan, waktuMulai, waktuSelesai, ketLog, buktiFoto);
            call.enqueue(new Callback<ResponLog>() {
                @Override
                public void onResponse(Call<ResponLog> call, Response<ResponLog> response) {
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
                                        Intent intent = new Intent(LogBook.this, LogBook.class);
                                        intent.putExtra("karyawan_kode", sharedPreferences.getString("karyawan_kode", null));
                                        startActivity(intent);
                                    }
                                });
                        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        alertDialogBuilder.setTitle("Informasi");
                        alertDialogBuilder
                                .setMessage("Tidak berhasil melakukan pencatatan Log Book")
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
                public void onFailure(Call<ResponLog> call, Throwable t) {
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
//                    Toast.makeText(LogBook.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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

