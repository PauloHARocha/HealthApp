package com.example.paulo.healthapp.Fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paulo.healthapp.Adapter.HistoricoAdapter;
import com.example.paulo.healthapp.Adapter.HistoricoListAdapter;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceMedicamento;

import java.util.Date;
import java.util.List;

public class HistoricoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        //Bundle bundle = getArguments();
        //Date data = (Date) bundle.getSerializable("data");
        List<ModelMedicamento> listaMedicamento = ServiceMedicamento.getList();
        //List<ModelHistoricoMedicamento> listaHistorico = ServiceHistoricoMedicamento.getListByDate(data,data);

        HistoricoAdapter adapter = new HistoricoAdapter(recyclerView.getContext(),listaMedicamento);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_info;
        public RecyclerView rv_lista_historico;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_historico,parent, false));
            tv_info = (TextView) itemView.findViewById(R.id.historico_tv_info);
            rv_lista_historico = (RecyclerView) itemView.findViewById(R.id.recycler_lista_historico);

        }

    }
}