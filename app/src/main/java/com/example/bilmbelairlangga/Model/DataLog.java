package com.example.bilmbelairlangga.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataLog {

        @SerializedName("idLogBook")
        @Expose
        private Integer idLogBook;
        @SerializedName("tglKegiatan")
        @Expose
        private String tglKegiatan;
        @SerializedName("waktuMulai")
        @Expose
        private String waktuMulai;
        @SerializedName("waktuSelesai")
        @Expose
        private String waktuSelesai;
        @SerializedName("ketLog")
        @Expose
        private String ketLog;

        public Integer getIdLogBook() {
            return idLogBook;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public String getTglKegiatan() {
            Locale indonesia = new Locale("ID");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy",indonesia);
            OffsetDateTime dateTime = OffsetDateTime.parse(tglKegiatan).plusDays(1);
            String tgl = dateTime.format(dateFormatter);
            return tgl;
        }

        public String getWaktuMulai() {
            return waktuMulai;
        }

        public String getWaktuSelesai() {
            return waktuSelesai;
        }

        public String getKetLog() {
            return ketLog;
        }
}

