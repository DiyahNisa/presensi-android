package com.diyahnisa.bilmbelairlangga.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataIzin {

        @SerializedName("idIzin")
        @Expose
        private Integer idIzin;
        @SerializedName("tglIzin")
        @Expose
        private String tglIzin;
        @SerializedName("tglSelesai")
        @Expose
        private String tglSelesai;
        @SerializedName("durasi")
        @Expose
        private String durasi;
        @SerializedName("ketIzin")
        @Expose
        private String ketIzin;

        public Integer getIdIzin() {
            return idIzin;
        }

        public void setIdIzin(Integer idIzin) {
            this.idIzin = idIzin;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public String getTglIzin() {
            Locale indonesia = new Locale("ID");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy",indonesia);
            OffsetDateTime dateTime = OffsetDateTime.parse(tglIzin).plusDays(1);
            String tgl = dateTime.format(dateFormatter);
            return tgl;
        }

        public void setTglIzin(String tglIzin) {
            this.tglIzin = tglIzin;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public String getTglSelesai() {
            Locale indonesia = new Locale("ID");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy",indonesia);
            OffsetDateTime dateTime = OffsetDateTime.parse(tglSelesai).plusDays(1);
            String tgl = dateTime.format(dateFormatter);
            return tgl;
        }

        public void setTglSelesai(String tglSelesai) {
            this.tglSelesai = tglSelesai;
        }

        public String getDurasi() {
            return durasi;
        }

        public void setDurasi(String durasi) {
            this.durasi = durasi;
        }

        public String getKetIzin() {
            return ketIzin;
        }

        public void setKetIzin(String ketIzin) {
            this.ketIzin = ketIzin;
        }

    }

