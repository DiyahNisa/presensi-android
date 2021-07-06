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

import com.example.bilmbelairlangga.Model.DataLog;
import com.example.bilmbelairlangga.R;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.HolderData> {
    private Context ctx;
    private List<DataLog> listLog;

    public LogAdapter(Context ctx, List<DataLog> listLog) {
        this.ctx = ctx;
        this.listLog = listLog;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_log,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataLog dl = listLog.get(position);

        holder.textId.setText(String.valueOf(dl.getIdLogBook()));
        holder.textTglKegiatan.setText(dl.getTglKegiatan());
        holder.textWaktuMulai.setText(dl.getWaktuMulai());
        holder.textWaktuSelesai.setText(dl.getWaktuSelesai());
        holder.textKetLog.setText(dl.getKetLog());
    }

    @Override
    public int getItemCount() {
        return listLog.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView textId, textTglKegiatan, textWaktuMulai,  textWaktuSelesai, textKetLog;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            textId = itemView.findViewById(R.id.textIdLog);
            textTglKegiatan = itemView.findViewById(R.id.textTglKegiatan);
            textWaktuMulai = itemView.findViewById(R.id.textWaktuMulai);
            textWaktuSelesai = itemView.findViewById(R.id.textWaktuSelesai);
            textKetLog = itemView.findViewById(R.id.textKetLog);
        }
    }
}

