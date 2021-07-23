package com.diyahnisa.bilmbelairlangga;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diyahnisa.bilmbelairlangga.Adapter.LogAdapter;
import com.diyahnisa.bilmbelairlangga.ApiService.ApiClient;
import com.diyahnisa.bilmbelairlangga.Model.DataLog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogBookView extends AppCompatActivity {

    RecyclerView Rv_Log;
    CardView logList;
    String  karyawan_kode;
    SharedPreferences sharedPreferences;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataLog> listLog = new ArrayList<>();

    public static final String BASE_URL = "http://192.168.0.104/airlanggaBimbel/public/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book_view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LogBookView.this);
        karyawan_kode = sharedPreferences.getString("karyawan_kode", null);
        Rv_Log = (RecyclerView) findViewById(R.id.Rv_log);
        lmData = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        Rv_Log.setLayoutManager(lmData);

        data();
    }

    public void data () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClient logBookApi = retrofit.create(ApiClient.class);
        Call<List<DataLog>> call = logBookApi.getLog1(karyawan_kode);
        call.enqueue(new Callback<List<DataLog>>() {
            @Override
            public void onResponse(Call<List<DataLog>> call, Response<List<DataLog>> response) {
                Toast.makeText(LogBookView.this, "Tampil Daftar Log Book", Toast.LENGTH_SHORT).show();
                listLog = response.body();

                adData = new LogAdapter(LogBookView.this, listLog);
                Rv_Log.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DataLog>> call, Throwable t) {
                Toast.makeText(LogBookView.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}