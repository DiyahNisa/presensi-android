package com.diyahnisa.bilmbelairlangga;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.diyahnisa.bilmbelairlangga.ApiService.LoginApi;
import com.diyahnisa.bilmbelairlangga.Model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

//    public static final String BASE_URL = "http://192.168.0.104/airlanggaBimbel/public/api/";
public static final String BASE_URL = "https://bimbelairlangga.my.id/public/api/";
    String username, password;
    String karyawan_kode, message, success;
    EditText editUsername, editPassword;
    Button btnLogin;
    static String KEY_USER = "nama_user";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Digunakan agar user tidak perlu login lagi apabila keluar tanpa menggunakan logout
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        String statusLogin = sharedPreferences.getString("status", "Logged Out");
        if (statusLogin.equals("Logged In")) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        username = editUsername.getText().toString();
        password = editPassword.getText().toString();
        if (username.isEmpty()) {
            editUsername.setError("Masukkan Username");
        } else if (password.isEmpty()) {
            editPassword.setError("Masukkan Password");
        } else {
            //membuat pesan dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            ProgressDialog progressDialog = new ProgressDialog(Login.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Tunggu sebentar...");
            progressDialog.show();
            //connect dengan api menggunakan retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LoginApi loginAPI = retrofit.create(LoginApi.class);
            Call<Value> call = loginAPI.login(username, password);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    success = response.body().getSuccess();
                    message = response.body().getMessage();
                    karyawan_kode = response.body().getKaryawan_kode();
                    progressDialog.dismiss();
                    if (success.equals("1")) {
                        gotoMainActivity();
                    } else {
                        alertDialogBuilder.setTitle("Informasi");
                        alertDialogBuilder
                                .setMessage(message)
                                .setCancelable(false)
                                .setNeutralButton("Oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
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
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

//                    Toast.makeText(Login.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void gotoMainActivity() {
        //menyimpan inputan dengan shared preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, message);
        editor.putString( "karyawan_kode", karyawan_kode);
                editor.putString("status", "Logged In");
                editor.apply();
                Intent intent = new Intent(Login.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        }