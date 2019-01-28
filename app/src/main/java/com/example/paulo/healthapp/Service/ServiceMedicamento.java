package com.example.paulo.healthapp.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;


import com.example.paulo.healthapp.Dao.Dao;
import com.example.paulo.healthapp.Model.ModelMedicamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceMedicamento
{

    public static ModelMedicamento getById(int id)
    {
        try
        {
            ModelMedicamento modelMedicamento = new ModelMedicamento();
            String query;

            query = "SELECT idMedicamento, nomeMedicamento, qtdMedicamento, corMedicamento, horaMedicamento, idTratamento, dataInicio, dataFinal, qtdTomados " +
                    "FROM medicamento                                              " +
                    "WHERE idMedicamento =                                         " + id;

            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                modelMedicamento.setIdMedicamento(cursor.getInt(0));
                modelMedicamento.setNomeMedicamento(cursor.getString(1));
                modelMedicamento.setQtdMedicamento(cursor.getInt(2));
                modelMedicamento.setCorMedicamento(cursor.getString(3));
                modelMedicamento.setHoraMedicamento(new Date(cursor.getLong(4)));
                modelMedicamento.setTratamento(ServiceTratamento.getById(cursor.getInt(5)));
                modelMedicamento.setDataInicial(new Date(cursor.getLong(6)));
                modelMedicamento.setDataFinal(new Date(cursor.getLong(7)));
                modelMedicamento.setQtdTomados(cursor.getInt(8));
            }
            return modelMedicamento;
        }
        catch (SQLiteException ex)
        {
            return null;
        }
    }

    public static List<ModelMedicamento> getList()
    {
        try
        {
            ArrayList<ModelMedicamento> lstModelMedicamento = new ArrayList<ModelMedicamento>();;
            String query;

            query = "SELECT idMedicamento, nomeMedicamento, qtdMedicamento, corMedicamento, horaMedicamento, idTratamento, dataInicio, dataFinal, possuiHistorico, qtdTomados " +
                    "FROM medicamento ;";


            Cursor cursor = null;
            cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelMedicamento modelMedicamento = new ModelMedicamento();

                    modelMedicamento.setIdMedicamento(cursor.getInt(0));
                    modelMedicamento.setNomeMedicamento(cursor.getString(1));
                    modelMedicamento.setQtdMedicamento(cursor.getInt(2));
                    modelMedicamento.setCorMedicamento(cursor.getString(3));
                    modelMedicamento.setHoraMedicamento(new Date(cursor.getLong(4)));
                    modelMedicamento.setTratamento(ServiceTratamento.getById(cursor.getInt(5)));
                    modelMedicamento.setDataInicial(new Date(cursor.getLong(6)));
                    modelMedicamento.setDataFinal(new Date(cursor.getLong(7)));
                    modelMedicamento.setPossuiHistorico(cursor.getInt(8));
                    modelMedicamento.setQtdTomados(cursor.getInt(9));

                    lstModelMedicamento.add(modelMedicamento);

                } while (cursor.moveToNext());
            }

            return lstModelMedicamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static List<ModelMedicamento> getListByPossuiHistorico(int possui)
    {
        try
        {
            ArrayList<ModelMedicamento> lstModelMedicamento = new ArrayList<ModelMedicamento>();;
            String query;

            query = "SELECT idMedicamento, nomeMedicamento, qtdMedicamento, corMedicamento, horaMedicamento, idTratamento, dataInicio, dataFinal, possuiHistorico, qtdTomados " +
                    "FROM medicamento " +
                    "WHERE possuiHistorico = " + possui;


            Cursor cursor = null;
            cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelMedicamento modelMedicamento = new ModelMedicamento();

                    modelMedicamento.setIdMedicamento(cursor.getInt(0));
                    modelMedicamento.setNomeMedicamento(cursor.getString(1));
                    modelMedicamento.setQtdMedicamento(cursor.getInt(2));
                    modelMedicamento.setCorMedicamento(cursor.getString(3));
                    modelMedicamento.setHoraMedicamento(new Date(cursor.getLong(4)));
                    modelMedicamento.setTratamento(ServiceTratamento.getById(cursor.getInt(5)));
                    modelMedicamento.setDataInicial(new Date(cursor.getLong(6)));
                    modelMedicamento.setDataFinal(new Date(cursor.getLong(7)));
                    modelMedicamento.setPossuiHistorico(cursor.getInt(8));
                    modelMedicamento.setQtdTomados(cursor.getInt(9));

                    lstModelMedicamento.add(modelMedicamento);

                } while (cursor.moveToNext());
            }

            return lstModelMedicamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public  static List<ModelMedicamento> getListByDate(Date date)
    {
        try
        {
            ArrayList<ModelMedicamento> lstModelMedicamento = new ArrayList<ModelMedicamento>();
            String query;

            query = "SELECT idMedicamento, nomeMedicamento, qtdMedicamento, corMedicamento, horaMedicamento, idTratamento, dataInicio, dataFinal, qtdTomados " +
                    "FROM medicamento " +
                    "WHERE dataInicio <= " + date.getTime() +
                    " AND dataFinal >= " + date.getTime();


            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelMedicamento modelMedicamento = new ModelMedicamento();

                    modelMedicamento.setIdMedicamento(cursor.getInt(0));
                    modelMedicamento.setNomeMedicamento(cursor.getString(1));
                    modelMedicamento.setQtdMedicamento(cursor.getInt(2));
                    modelMedicamento.setCorMedicamento(cursor.getString(3));
                    modelMedicamento.setHoraMedicamento(new Date(cursor.getLong(4)));
                    modelMedicamento.setTratamento(ServiceTratamento.getById(cursor.getInt(5)));
                    modelMedicamento.setDataInicial(new Date(cursor.getLong(6)));
                    modelMedicamento.setDataFinal(new Date(cursor.getLong(7)));
                    modelMedicamento.setQtdTomados(cursor.getInt(8));

                    lstModelMedicamento.add(modelMedicamento);

                } while (cursor.moveToNext());
            }

            return lstModelMedicamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static List<ModelMedicamento> getListByTratamento(int id)
    {
        try
        {
            ArrayList<ModelMedicamento> lstModelMedicamento = new ArrayList<ModelMedicamento>();
            String query;

            query = "SELECT idMedicamento, nomeMedicamento, qtdMedicamento, corMedicamento, horaMedicamento, idTratamento, dataInicio, dataFinal, qtdTomados " +
                    "FROM medicamento " +
                    "WHERE idTratamento = " + id;

            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelMedicamento modelMedicamento = new ModelMedicamento();

                    modelMedicamento.setIdMedicamento(cursor.getInt(0));
                    modelMedicamento.setNomeMedicamento(cursor.getString(1));
                    modelMedicamento.setQtdMedicamento(cursor.getInt(2));
                    modelMedicamento.setCorMedicamento(cursor.getString(3));
                    modelMedicamento.setHoraMedicamento(new Date(cursor.getLong(4)));
                    modelMedicamento.setTratamento(ServiceTratamento.getById(cursor.getInt(5)));
                    modelMedicamento.setDataInicial(new Date(cursor.getLong(6)));
                    modelMedicamento.setDataFinal(new Date(cursor.getLong(7)));
                    modelMedicamento.setQtdTomados(cursor.getInt(8));


                    lstModelMedicamento.add(modelMedicamento);

                } while (cursor.moveToNext());
            }

            return lstModelMedicamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static void insert(ModelMedicamento modelMedicamento)
    {
        try
        {
            String query =  "INSERT INTO medicamento (nomeMedicamento, qtdMedicamento, corMedicamento, horaMedicamento, idTratamento, dataInicio, dataFinal, qtdTomados) " +
                            "VALUES ('"   + modelMedicamento.getNomeMedicamento()              + "'" +
                                      "," + modelMedicamento.getQtdMedicamento()               +
                                      ",'"+ modelMedicamento.getCorMedicamento()               + "'" +
                                      "," + modelMedicamento.getHoraMedicamento().getTime()    +
                                      "," + modelMedicamento.getTratamento().getIdTratamento() +
                                      "," + modelMedicamento.getDataInicial().getTime()        +
                                      "," + modelMedicamento.getDataFinal().getTime()          +
                                      "," + modelMedicamento.getQtdTomados()                   +

                            ");";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (SQLiteException ex)
        {

        }
    }

    public static void update(ModelMedicamento modelMedicamento)
    {
        try
        {
            String query =  "UPDATE medicamento     " +
                            "SET nomeMedicamento = '" + modelMedicamento.getNomeMedicamento()           + "'" +
                            "   ,qtdMedicamento  =  " + modelMedicamento.getQtdMedicamento()            +
                            "   ,corMedicamento  = '" + modelMedicamento.getCorMedicamento()            + "'" +
                            "   ,horaMedicamento =  " + modelMedicamento.getHoraMedicamento().getTime() +
                            "   ,dataInicio      =  " + modelMedicamento.getDataInicial().getTime()     +
                            "   ,dataFinal       =  " + modelMedicamento.getDataFinal().getTime()       +
                            "   ,possuiHistorico =  " + modelMedicamento.getPossuiHistorico()           +
                            "   ,qtdTomados      =  " + modelMedicamento.getQtdTomados()                +
                            " WHERE idMedicamento = " + modelMedicamento.getIdMedicamento()          +";";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);

        }
        catch (SQLiteException ex)
        {

        }
    }
}
