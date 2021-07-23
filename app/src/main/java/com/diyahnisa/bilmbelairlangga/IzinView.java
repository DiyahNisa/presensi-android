package com.diyahnisa.bilmbelairlangga;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diyahnisa.bilmbelairlangga.Adapter.IzinAdapter;
import com.diyahnisa.bilmbelairlangga.ApiService.ApiClient;
import com.diyahnisa.bilmbelairlangga.Model.DataIzin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IzinView extends AppCompatActivity {

    RecyclerView Rv_Izin;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataIzin> listIzin = new ArrayList<>();
    ProgressDialog pd;
    String karyawan_kode;
    SharedPreferences sharedPreferences;
    public static final String BASE_URL = "http://192.168.0.104/airlanggaBimbel/public/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin_view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(IzinView.this);
        karyawan_kode = sharedPreferences.getString("karyawan_kode", null);

        pd = new ProgressDialog(this);
        Rv_Izin = findViewById(R.id.Rv_izin);
        lmData = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        Rv_Izin.setLayoutManager(lmData);

        data();
    }

    public void data () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClient izinApi = retrofit.create(ApiClient.class);
        Call<List<DataIzin>> call = izinApi.getIzin(karyawan_kode);
        call.enqueue(new Callback<List<DataIzin>>() {
            @Override
            public void onResponse(Call<List<DataIzin>> call, Response<List<DataIzin>> response) {
                Toast.makeText(IzinView.this, "Tampil Daftar Izin", Toast.LENGTH_SHORT).show();
                listIzin = response.body();

                adData = new IzinAdapter(IzinView.this, listIzin);
                Rv_Izin.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DataIzin>> call, Throwable t) {
                pd.hide();
                Toast.makeText(IzinView.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}