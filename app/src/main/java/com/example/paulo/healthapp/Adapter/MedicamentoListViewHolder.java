package com.example.paulo.healthapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.paulo.healthapp.R;

public class MedicamentoListViewHolder extends RecyclerView.ViewHolder {

    final TextView info;
    //final ImageView cor;
    final RelativeLayout layout;

    public MedicamentoListViewHolder(final View view, final Context context) {
        super(view);
        info = (TextView) view.findViewById(R.id.item_medicamento_info);
        //cor = (ImageView) view.findViewById(R.id.item_medicamento_cor);
        layout = (RelativeLayout) view.findViewById(R.id.item_medicamento_layout);

    }

}
