package com.example.paulo.healthapp.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paulo.healthapp.AlertReceiver;
import com.example.paulo.healthapp.Fragment.PrincipalFragment;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceMedicamento;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class PrincipalAdapter extends RecyclerView.Adapter<PrincipalFragment.ViewHolder> {
    private List<ModelHistoricoMedicamento> listaHistoricoMedicamento;//lista de medicamentos
    private Context context;

    public PrincipalAdapter(Context context,  List<ModelHistoricoMedicamento> listaHistoricoMedicamento) {

        this.listaHistoricoMedicamento = listaHistoricoMedicamento;
        this.context = context;
    }

    @Override
    public PrincipalFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrincipalFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(final PrincipalFragment.ViewHolder holder, final int position) {
        final ModelHistoricoMedicamento historicoMedicamento = listaHistoricoMedicamento.get(position);
        //adiciona a lista de pilulas de acordo com quantidade necessaria para cada medicamento
        final PilulaListAdapter pilulaListAdapter = new PilulaListAdapter(historicoMedicamento, context);
        holder.recyclerView.setAdapter(pilulaListAdapter);
        RecyclerView.LayoutManager layout = new GridLayoutManager(context, 2);
        holder.recyclerView.setLayoutManager(layout);

        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        String info = formato.format(historicoMedicamento.getMedicamento().getHoraMedicamento());
        holder.info_medicamento.setText(info);

        if (historicoMedicamento.getMedicamento().getTratamento().getNomeTratamento().equals("Fase Intensiva")){
            holder.layout_background.setBackgroundResource(R.color.vermelho);
        }else{
            holder.layout_background.setBackgroundResource(R.color.branco);
        }

       /* switch (historicoMedicamento.getMedicamento().getCorMedicamento()){
            case "Amarelo":
                holder.layout_background.setBackgroundResource(R.color.amarelo);
                break;
            case "Azul":
                holder.layout_background.setBackgroundResource(R.color.azul);
                break;
            case "Verde":
                holder.layout_background.setBackgroundResource(R.color.verde);
                break;
            case "Vermelho":
                holder.layout_background.setBackgroundResource(R.color.vermelho);
                break;
            case "Roxo":
                holder.layout_background.setBackgroundResource(R.color.roxo);
                break;
            case "Rosa":
                holder.layout_background.setBackgroundResource(R.color.rosa);
                break;
            default:
        }*/
        /*final Boolean estado;
        if(historicoMedicamento.getQtdMedicamento() == historicoMedicamento.getQtdTomados()){
            estado = true;
            holder.confirmar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.confirmar.setBackgroundColor(context.getResources().getColor(R.color.cinza));
            estado = false;
        }

        holder.confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Calendar horaReal = Calendar.getInstance();
                horaReal.set(0, 0, 0, 0, 0, 0);
                if (estado) {
                    historicoMedicamento.setQtdTomados(0);
                    historicoMedicamento.setHoraReal(horaReal.getTime());
                } else {
                    historicoMedicamento.setQtdTomados(historicoMedicamento.getQtdMedicamento());
                    horaReal.setTime(new Date());
                    historicoMedicamento.setHoraReal(horaReal.getTime());
                }


                ServiceHistoricoMedicamento.update(historicoMedicamento);
                PrincipalAdapter.this.notifyDataSetChanged();

                dialogConfirmarMedicamentoTomado(historicoMedicamento, position);
            }
        });
        */

    }

    /*private void dialogConfirmarMedicamentoTomado(final ModelHistoricoMedicamento historicoMedicamento, final int position){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        alertBuilder.setTitle("Confirma ter tomado " + historicoMedicamento.getQtdTomados() + " comprimidos?");

        alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                historicoMedicamento.setConfirmado(1);
                Log.e("confirmadoHistorico", historicoMedicamento.getConfirmado() + "");
                ServiceHistoricoMedicamento.updateConfirmado(historicoMedicamento);
                int confirmado = ServiceHistoricoMedicamento.getById(historicoMedicamento.getIdHistoricoMedicamento()).getConfirmado();
                Log.e("confirmadoHistoricoDepo", confirmado + "");

                listaHistoricoMedicamento.remove(historicoMedicamento);

                PrincipalAdapter.this.notifyItemRemoved(position);
                dialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }*/

    @Override
    public int getItemCount() {
        return this.listaHistoricoMedicamento != null ? this.listaHistoricoMedicamento.size() : 0;
    }

}



