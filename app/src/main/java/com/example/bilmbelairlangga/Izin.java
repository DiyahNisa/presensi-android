package com.example.bilmbelairlangga;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bilmbelairlangga.ApiService.ApiClient;
import com.example.bilmbelairlangga.Model.ResponIzin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Izin extends AppCompatActivity {

    EditText editTglIzin, editTglMasuk, editDurasi;
    ImageView viewSurat;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    Button btnIzin, btnLihatIzin, btnSurat;
    String  mediaPath, postPath;
    SharedPreferences sharedPreferences;

    private  int REQ_IMG = 21;
    public static final int REQUEST_WRITE_PERMISSION = 786;
    public static final String BASE_URL = "http://192.168.0.104/airlanggaBimbel/public/api/";

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            kirimIzin();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Izin.this);

        editDurasi=(EditText) findViewById(R.id.editDurasi);
        viewSurat = (ImageView) findViewById(R.id.viewSurat);
        dateFormat = new SimpleDateFormat("dd-MM-yyy");

        editTglIzin=(EditText) findViewById(R.id.editTglIzin);
        editTglIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(Izin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,month,dayOfMonth);
                        editTglIzin.setText(dateFormat.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        editTglMasuk=(EditText)findViewById(R.id.editTglMasuk);
        editTglMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(Izin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,month,dayOfMonth);
                        editTglMasuk.setText(dateFormat.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnLihatIzin = (Button) findViewById(R.id.btnLihatIzin);
        btnLihatIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godata = new Intent(Izin.this, IzinView.class);
                godata.putExtra("karyawan_id", sharedPreferences.getString("karyawan_id",null));
                startActivity(godata);
            }
        });

        btnSurat = (Button) findViewById(R.id.btnSurat);
        btnSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQ_IMG);
            }
        });

        btnIzin = (Button) findViewById(R.id.btnIzin);
        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_izin);
        bottomNavigationView.setSelectedItemId(R.id.nav_izin);
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
                        startActivity(new Intent(getApplicationContext()
                                , LogBook.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.nav_izin:
                        return false;
                }
                return false;
            }
        });
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            kirimIzin();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_IMG) {
                if (data != null) {
                    Uri selectedFile = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedFile, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    viewSurat.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }

    }

    public void kirimIzin() {
        if (mediaPath== null)
        {
            Toast.makeText(getApplicationContext(), "Pilih gambar terlebih dahul ...", Toast.LENGTH_LONG).show();
        } else {
            File imageFile = new File(mediaPath);

            RequestBody karyawan_id = RequestBody.create(MultipartBody.FORM, sharedPreferences.getString("karyawan_id", null));
            RequestBody tglIzin = RequestBody.create(MultipartBody.FORM, editTglIzin.getText().toString());
            RequestBody tglSelesai = RequestBody.create(MultipartBody.FORM, editTglMasuk.getText().toString());
            RequestBody durasi = RequestBody.create(MultipartBody.FORM, editDurasi.getText().toString());
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
            MultipartBody.Part buktiSurat = MultipartBody.Part.createFormData("buktiSurat", imageFile.getName(), reqBody);
            Log.d("LIhat1", imageFile.getName());


            //membuat pesan dialog
            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
            ProgressDialog progressDialog = new ProgressDialog(Izin.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Tunggu sebentar...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiClient izinApi = retrofit.create(ApiClient.class);
            Call<ResponIzin> call = izinApi.addIzin(karyawan_id, tglIzin, tglSelesai, durasi, buktiSurat);
            call.enqueue(new Callback<ResponIzin>() {
                @Override
                public void onResponse(Call<ResponIzin> call, Response<ResponIzin> response) {
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
                                        Intent intent = new Intent(Izin.this, IzinView.class);
                                        startActivity(intent);
                                    }
                                });
                        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
//                        Toast.makeText(Presensi.this, "Tidak berhasil melakukan presensi", Toast.LENGTH_SHORT).show();
                        alertDialogBuilder.setTitle("Informasi");
                        alertDialogBuilder
                                .setMessage("Tidak berhasil melakukan permohonan izin")
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
                public void onFailure(Call<ResponIzin> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                    Toast.makeText(Izin.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

//    Menu LogOut
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
                                startActivity(new Intent(Izin.this, Login.class));
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
