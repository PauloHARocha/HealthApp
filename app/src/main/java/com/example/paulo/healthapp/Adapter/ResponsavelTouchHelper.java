package com.example.paulo.healthapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class ResponsavelTouchHelper extends ItemTouchHelper.SimpleCallback {
    private ResponsavelListAdapter responsavelAdapter;

    public ResponsavelTouchHelper(ResponsavelListAdapter responsavelAdapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.responsavelAdapter = responsavelAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Remove item
        responsavelAdapter.remove(viewHolder.getAdapterPosition());
    }
}
