package com.example.paulo.healthapp.Fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paulo.healthapp.Adapter.ResponsavelListAdapter;
import com.example.paulo.healthapp.Adapter.ResponsavelTouchHelper;
import com.example.paulo.healthapp.Model.ModelResponsavel;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceResponsavel;

import java.util.List;


public class ResponsavelListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ModelResponsavel> listaResponsavel;
        listaResponsavel = ServiceResponsavel.getList();
        ResponsavelListAdapter adapter = new ResponsavelListAdapter(recyclerView.getContext(),listaResponsavel);
        recyclerView.setAdapter(adapter);

        /*ItemTouchHelper.Callback callback = new ResponsavelTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);*/

        return view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ib_editar;
        public LinearLayout ll_info;
        public TextView tv_nome;
        public TextView tv_cpf;
        public TextView tv_telefone;
        public TextView tv_endereco;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_responsavel, parent, false));

            ib_editar = (ImageButton) itemView.findViewById(R.id.item_responsavel_edit_button);
            ll_info = (LinearLayout) itemView.findViewById(R.id.item_responsavel_info);
            tv_nome = (TextView) itemView.findViewById(R.id.item_responsavel_nome);
            tv_cpf = (TextView) itemView.findViewById(R.id.item_responsavel_cpf);
            tv_telefone = (TextView) itemView.findViewById(R.id.item_responsavel_telefone);
            tv_endereco = (TextView) itemView.findViewById(R.id.item_responsavel_endereco);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ll_info.getVisibility()== View.GONE)
                        ll_info.setVisibility(View.VISIBLE);
                    else
                        ll_info.setVisibility(View.GONE);
                }
            });

        }
    }

}

