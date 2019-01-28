package com.example.paulo.healthapp.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paulo.healthapp.Adapter.TratamentoListAdapter;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceTratamento;

import java.util.ArrayList;
import java.util.List;


public class TratamentoListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(
                R.layout.recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ModelTratamento> listaTratamento = new ArrayList<>();
        listaTratamento = ServiceTratamento.getList();
        TratamentoListAdapter adapter = new TratamentoListAdapter(recyclerView.getContext(),listaTratamento);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ib_add;
        public TextView tv_nome;
        public RecyclerView recyclerView;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_tratamento, parent, false));

            ib_add = (ImageButton) itemView.findViewById(R.id.item_tratamento_add_button);
            tv_nome = (TextView) itemView.findViewById(R.id.item_tratamento_nome);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_tratamento);

        }
    }

}
