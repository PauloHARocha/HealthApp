package com.example.paulo.healthapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulo.healthapp.Fragment.MessageDialogFragment;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceMedicamento;
import com.example.paulo.healthapp.Service.ServiceTratamento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class PilulaListAdapter extends RecyclerView.Adapter {

    private ModelHistoricoMedicamento modelHistoricoMedicamento;
    private ModelTratamento modelTratamento;
    private Context context;
    private ArrayList<Boolean> estado;

    private TextView tv_message;
    private View dialog_view;

    public PilulaListAdapter(ModelHistoricoMedicamento modelHistoricoMedicamento, Context context) {
        this.modelHistoricoMedicamento = modelHistoricoMedicamento;
        this.modelTratamento = modelHistoricoMedicamento.getMedicamento().getTratamento();
        this.context = context;
        this.estado = new ArrayList<>();
        for (int i = 0; i < modelHistoricoMedicamento.getQtdMedicamento(); i++) {
            estado.add(false);
        }
        for (int i = 0; i < modelHistoricoMedicamento.getQtdTomados(); i++) {
            estado.set(i, true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pilula, parent, false);
        PilulaListViewHolder holder = new PilulaListViewHolder(view, context);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final PilulaListViewHolder holder = (PilulaListViewHolder) viewHolder;
        if (estado.get(position)) {
            holder.pilula.setImageResource(R.mipmap.pilula_verde);
        } else {
            holder.pilula.setImageResource(R.mipmap.pilula_cinza);
        }
        holder.pilula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar horaReal = Calendar.getInstance();
                horaReal.set(0, 0, 0, 0, 0, 0);
                /*if(estado.get(position)){
                    holder.pilula.setImageResource(R.mipmap.pilula_cinza);
                    modelHistoricoMedicamento.setQtdTomados(modelHistoricoMedicamento.getQtdTomados() - 1);
                    modelHistoricoMedicamento.setHoraReal(horaReal.getTime());
                    ServiceHistoricoMedicamento.update(modelHistoricoMedicamento);
                    modelTratamento.setDoses(modelTratamento.getDoses() -1);
                    ServiceTratamento.update(modelTratamento);
                    Log.e("dosesModel:",modelTratamento.getDoses() + "");
                    Log.e("dosesDAO:",ServiceTratamento.getById(modelTratamento.getIdTratamento()).getDoses()+ "");
                    estado.set(position,false);
                }*/
                if (!estado.get(position)) {
                    holder.pilula.setImageResource(R.mipmap.pilula_verde);
                    modelHistoricoMedicamento.setQtdTomados(modelHistoricoMedicamento.getQtdTomados() + 1);
                    horaReal.setTime(new Date());
                    modelHistoricoMedicamento.setHoraReal(horaReal.getTime());
                    ServiceHistoricoMedicamento.update(modelHistoricoMedicamento);
                    estado.set(position, true);
                    if (modelHistoricoMedicamento.getQtdMedicamento() == modelHistoricoMedicamento.getQtdTomados()) {
                        modelTratamento.setDoses(modelTratamento.getDoses() - 1);
                        ServiceTratamento.update(modelTratamento);
                        Log.e("dosesModel:", modelTratamento.getDoses() + "");
                        Log.e("dosesDAO:", ServiceTratamento.getById(modelTratamento.getIdTratamento()).getDoses() + "");
                        Toast.makeText(context, "Parabéns, continue o tratamento", Toast.LENGTH_LONG).show();
                        createMessageDialog();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.modelHistoricoMedicamento.getQtdMedicamento();
    }

    private void createMessageDialog(){

        AlertDialog.Builder messageDialog = new AlertDialog.Builder(context);

        dialog_view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_message, null);

        messageDialog.setView(dialog_view);

        tv_message = (TextView) dialog_view.findViewById(R.id.mensagem_tuberculose);

        Random rand = new Random();
        int n = rand.nextInt(19) + 1; // Gives n such that 1 <= n <= 19

        String message = "mensagem" + n;

        tv_message.setText(context.getString(context.getResources().getIdentifier(message,"string",context.getPackageName())));

        messageDialog.show();
        }
    }

