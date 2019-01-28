package com.example.paulo.healthapp.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import com.example.paulo.healthapp.Dao.Dao;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.Model.ModelTratamento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class ServiceHistoricoMedicamento
{

    public static ModelHistoricoMedicamento getById(int id)
    {
        try
        {
            ModelHistoricoMedicamento modelHistoricoMedicamento = new ModelHistoricoMedicamento();
            String query;

            query = "SELECT idHistoricoMedicamento, horaPrevista, horaReal, qtdMedicamento, qtdTomados, idMedicamento, confirmado " +
                    "FROM historicomedicamento                                     " +
                    "WHERE idHistoricoMedicamento =                                " + id;

            Cursor cursor = null;

            cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                modelHistoricoMedicamento.setIdHistoricoMedicamento(cursor.getInt(0));
                modelHistoricoMedicamento.setHoraPrevista(new Date(cursor.getLong(1)));
                modelHistoricoMedicamento.setHoraReal(new Date(cursor.getLong(2)));
                modelHistoricoMedicamento.setQtdMedicamento(cursor.getInt(3));
                modelHistoricoMedicamento.setQtdTomados(cursor.getInt(4));
                modelHistoricoMedicamento.setMedicamento(ServiceMedicamento.getById(cursor.getInt(5)));
                modelHistoricoMedicamento.setConfirmado(cursor.getInt(6));
            }

            return modelHistoricoMedicamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static List<ModelHistoricoMedicamento> getListByIdMedicamento(int idMedicamento){
        try
        {
            Calendar dataDia = Calendar.getInstance();
            dataDia.setTime(new Date());
            dataDia.set(Calendar.HOUR_OF_DAY, 23);
            dataDia.set(Calendar.MINUTE, 59);
            dataDia.set(Calendar.SECOND, 59);
            dataDia.set(Calendar.MILLISECOND, 0);

            ArrayList<ModelHistoricoMedicamento> lstModelHistoricoMedicamento = new ArrayList<ModelHistoricoMedicamento>();
            String query;

            query = "SELECT idHistoricoMedicamento, horaPrevista, horaReal, qtdMedicamento, qtdTomados, idMedicamento, confirmado " +
                    "FROM historicomedicamento " +
                    "WHERE idMedicamento = "+ idMedicamento +
                    " AND horaPrevista <= " + dataDia.getTime().getTime() ;

            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            int contDoses = 0;
            int qtdDoses;
            String tipo_tratamento = ServiceMedicamento.getById(idMedicamento).getTratamento().getNomeTratamento();
            if(Objects.equals(tipo_tratamento, "Fase Intensiva")){
                qtdDoses = 60;
            }else{
                qtdDoses = 120;
            }

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelHistoricoMedicamento modelHistoricoMedicamento = new ModelHistoricoMedicamento();

                    modelHistoricoMedicamento.setIdHistoricoMedicamento(cursor.getInt(0));
                    modelHistoricoMedicamento.setHoraPrevista(new Date(cursor.getLong(1)));
                    modelHistoricoMedicamento.setHoraReal(new Date(cursor.getLong(2)));
                    modelHistoricoMedicamento.setQtdMedicamento(cursor.getInt(3));
                    modelHistoricoMedicamento.setQtdTomados(cursor.getInt(4));
                    modelHistoricoMedicamento.setMedicamento(ServiceMedicamento.getById(cursor.getInt(5)));
                    modelHistoricoMedicamento.setConfirmado(cursor.getInt(6));

                    if(contDoses < qtdDoses){
                        if(modelHistoricoMedicamento.getQtdMedicamento()==modelHistoricoMedicamento.getQtdTomados()){
                            contDoses++;
                        }
                        lstModelHistoricoMedicamento.add(modelHistoricoMedicamento);
                    }

                } while (cursor.moveToNext());
            }

            return lstModelHistoricoMedicamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static List<ModelHistoricoMedicamento> getListByDate(Date dataInicial, Date dataFinal)
    {
        try
        {
            Calendar newData = Calendar.getInstance();

            newData.setTime(dataInicial);
            newData.set(Calendar.HOUR, 0);
            newData.set(Calendar.MINUTE, 0);
            newData.set(Calendar.SECOND, 0);
            newData.set(Calendar.MILLISECOND, 0);
            newData.set(Calendar.HOUR_OF_DAY, 0);
            dataInicial = newData.getTime();

            newData.setTime(dataFinal);
            newData.set(Calendar.HOUR, 23);
            newData.set(Calendar.MINUTE, 59);
            newData.set(Calendar.SECOND, 59);
            newData.set(Calendar.MILLISECOND, 0);
            newData.set(Calendar.HOUR_OF_DAY, 23);
            dataFinal = newData.getTime();

            ArrayList<ModelHistoricoMedicamento> lstModelHistoricoMedicamento = new ArrayList<ModelHistoricoMedicamento>();
            String query;

            query = "SELECT idHistoricoMedicamento, horaPrevista, horaReal, qtdMedicamento, qtdTomados, idMedicamento, confirmado " +
                    "FROM historicomedicamento " +
                    "WHERE horaPrevista BETWEEN "+dataInicial.getTime()+" AND "+dataFinal.getTime()+ ";";


            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelHistoricoMedicamento modelHistoricoMedicamento = new ModelHistoricoMedicamento();

                    modelHistoricoMedicamento.setIdHistoricoMedicamento(cursor.getInt(0));
                    modelHistoricoMedicamento.setHoraPrevista(new Date(cursor.getLong(1)));
                    modelHistoricoMedicamento.setHoraReal(new Date(cursor.getLong(2)));
                    modelHistoricoMedicamento.setQtdMedicamento(cursor.getInt(3));
                    modelHistoricoMedicamento.setQtdTomados(cursor.getInt(4));
                    modelHistoricoMedicamento.setMedicamento(ServiceMedicamento.getById(cursor.getInt(5)));
                    modelHistoricoMedicamento.setConfirmado(cursor.getInt(6));

                    ModelTratamento tratamento = ServiceTratamento.getById(modelHistoricoMedicamento.getMedicamento().getTratamento().getIdTratamento());

                    if(tratamento.getDoses() > 0)
                        lstModelHistoricoMedicamento.add(modelHistoricoMedicamento);

                } while (cursor.moveToNext());
            }


            return lstModelHistoricoMedicamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static void refreshNewMedicamentos()
    {
        try
        {
            List<ModelMedicamento> lstModelMedicamento = ServiceMedicamento.getListByPossuiHistorico(0);

            for(ModelMedicamento it : lstModelMedicamento)
            {
                ModelHistoricoMedicamento modelHistoricoMedicamento = new ModelHistoricoMedicamento();
                ModelMedicamento modelMedicamento = it;
                modelHistoricoMedicamento.setMedicamento(modelMedicamento);
                modelHistoricoMedicamento.setQtdMedicamento(modelMedicamento.getQtdMedicamento());
                modelHistoricoMedicamento.setQtdTomados(modelMedicamento.getQtdTomados());

                Calendar newData = Calendar.getInstance();

                newData.setTime(modelMedicamento.getDataInicial());
                newData.set(Calendar.HOUR, 0);
                newData.set(Calendar.MINUTE, 0);
                newData.set(Calendar.SECOND, 0);
                newData.set(Calendar.MILLISECOND, 0);
                newData.set(Calendar.HOUR_OF_DAY, 0);
                modelMedicamento.setDataInicial(newData.getTime());

                newData.setTime(modelMedicamento.getDataFinal());
                newData.set(Calendar.HOUR, 23);
                newData.set(Calendar.MINUTE, 59);
                newData.set(Calendar.SECOND, 59);
                newData.set(Calendar.MILLISECOND, 0);
                newData.set(Calendar.HOUR_OF_DAY, 23);
                modelMedicamento.setDataFinal(newData.getTime());

                insert(modelHistoricoMedicamento);

                modelMedicamento.setPossuiHistorico(1);
                ServiceMedicamento.update(modelMedicamento);
            }
        }
        catch (SQLiteException ex)
        {

        }
    }

    public static void insert(ModelHistoricoMedicamento modelHistoricoMedicamento)
    {
        try {
            Date ini = modelHistoricoMedicamento.getMedicamento().getDataInicial();

            Calendar fin = Calendar.getInstance();

            fin.setTime(modelHistoricoMedicamento.getMedicamento().getDataFinal());

            fin.add(Calendar.DAY_OF_MONTH, 20);


            Calendar c = Calendar.getInstance();
            c.setTime(ini);

            while(fin.getTime().after(c.getTime()))
            {
                String query = "INSERT INTO historicomedicamento (horaPrevista, qtdMedicamento, qtdTomados, idMedicamento) " +
                        "VALUES ( " + c.getTime().getTime() +
                        ",'" + modelHistoricoMedicamento.getQtdMedicamento() + "'" +
                        ",'" + modelHistoricoMedicamento.getQtdTomados() + "'" +
                        "," + modelHistoricoMedicamento.getMedicamento().getIdMedicamento() +
                        ");";
                Dao.getInstance(null).getWritableDatabase().execSQL(query);

                c.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        catch (SQLiteException ex)
        {

        }
    }

    public static void update(ModelHistoricoMedicamento modelHistoricoMedicamento)
    {
        try
        {
            String query =  "UPDATE historicomedicamento       "+
                            "SET  horaPrevista              =  "+ modelHistoricoMedicamento.getHoraPrevista().getTime() +
                            "    ,horaReal                  =  "+ modelHistoricoMedicamento.getHoraReal().getTime()     +
                            "    ,qtdMedicamento                  =  "+ modelHistoricoMedicamento.getQtdMedicamento()     +
                            "    ,qtdTomados         = '"+ modelHistoricoMedicamento.getQtdTomados()      +"' "+
                            "WHERE idHistoricoMedicamento  =  "+ modelHistoricoMedicamento.getIdHistoricoMedicamento() +";";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (Exception ex)
        {

        }
    }
    /*public static void updateConfirmado(ModelHistoricoMedicamento modelHistoricoMedicamento)
    {
        try
        {
            String query =  "UPDATE historicomedicamento       "+
                    "SET confirmado         = '"+ modelHistoricoMedicamento.getConfirmado()      +"' "+
                    "WHERE idHistoricoMedicamento  =  "+ modelHistoricoMedicamento.getIdHistoricoMedicamento() +";";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (Exception ex)
        {

        }
    }*/
}