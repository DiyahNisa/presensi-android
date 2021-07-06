package com.example.bilmbelairlangga.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bilmbelairlangga.Model.DataIzin;
import com.example.bilmbelairlangga.R;

import java.util.List;

public class IzinAdapter extends RecyclerView.Adapter<IzinAdapter.HolderData> {
    private Context ctx;
    private List<DataIzin> listIzin;

    public IzinAdapter(Context ctx, List<DataIzin> listIzin) {
        this.ctx = ctx;
        this.listIzin = listIzin;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_izin,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataIzin dI = listIzin.get(position);

        holder.textId.setText(String.valueOf(dI.getIdIzin()));
        holder.textMulai.setText(dI.getTglIzin());
        holder.textSelesai.setText(dI.getTglSelesai());
        holder.durasi.setText(dI.getDurasi());
        holder.ketIzin.setText(dI.getKetIzin());
    }

    @Override
    public int getItemCount() {
        return listIzin.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView textId, textMulai, textSelesai,  durasi, ketIzin;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            textId = itemView.findViewById(R.id.textIdIzin);
            textMulai = itemView.findViewById(R.id.textMulai);
            textSelesai = itemView.findViewById(R.id.textSelesai);
            durasi = itemView.findViewById(R.id.durasi);
            ketIzin = itemView.findViewById(R.id.ketIzin);
        }
    }
}