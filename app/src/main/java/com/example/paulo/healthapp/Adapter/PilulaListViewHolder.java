package com.example.paulo.healthapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.paulo.healthapp.R;

public class PilulaListViewHolder extends RecyclerView.ViewHolder {

    final ImageView pilula;

    public PilulaListViewHolder(final View view, final Context context) {
        super(view);
        pilula = (ImageView) view.findViewById(R.id.principal_imgv_pilula);
    }

}