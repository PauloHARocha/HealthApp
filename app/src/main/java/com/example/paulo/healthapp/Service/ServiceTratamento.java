package com.example.paulo.healthapp.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;


import com.example.paulo.healthapp.Dao.Dao;
import com.example.paulo.healthapp.Model.ModelTratamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceTratamento
{

    public static ModelTratamento getById(int id)
    {
        try
        {
            ModelTratamento modelTratamento =  new ModelTratamento();
            String query;

            query = "SELECT idTratamento, nomeTratamento, dataInicio, dataTermino, idPaciente, idResponsavel, doses " +
                    "FROM tratamento                                              " +
                    "WHERE idTratamento =                                         " + id;

            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor != null)
            {
                cursor.moveToFirst();
                modelTratamento.setIdTratamento(cursor.getInt(0));
                modelTratamento.setNomeTratamento(cursor.getString(1));
                modelTratamento.setDataInicio(new Date(cursor.getLong(2)));
                modelTratamento.setDataTermino(new Date(cursor.getLong(3)));
                modelTratamento.setPaciente(ServicePaciente.getById(cursor.getInt(4)));
                modelTratamento.setResponsavel(ServiceResponsavel.getById(cursor.getInt(5)));
                modelTratamento.setDoses(cursor.getInt(6));
            }
            return modelTratamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static List<ModelTratamento> getList()
    {
        try
        {
            ArrayList<ModelTratamento> lstModelTratamento = new ArrayList<ModelTratamento>();
            String query;

            query = "SELECT idTratamento, nomeTratamento, dataInicio, dataTermino, idPaciente, idResponsavel, doses " +
                    "FROM tratamento;";

            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelTratamento modelTratamento = new ModelTratamento();

                    modelTratamento.setIdTratamento(cursor.getInt(0));
                    modelTratamento.setNomeTratamento(cursor.getString(1));
                    modelTratamento.setDataInicio(new Date(cursor.getLong(2)));
                    modelTratamento.setDataTermino(new Date(cursor.getLong(3)));
                    modelTratamento.setPaciente(ServicePaciente.getById(cursor.getInt(4)));
                    modelTratamento.setResponsavel(ServiceResponsavel.getById(cursor.getInt(5)));
                    modelTratamento.setDoses(cursor.getInt(6));

                    lstModelTratamento.add(modelTratamento);

                } while (cursor.moveToNext());
            }

            return lstModelTratamento;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static void insert(ModelTratamento modelTratamento)
    {
        try
        {
            String query = "INSERT INTO tratamento (nomeTratamento, dataInicio, dataTermino, idPaciente, idResponsavel, doses) " +
                           "VALUES ('"  + modelTratamento.getNomeTratamento()                 + "'" +
                                    "," + modelTratamento.getDataInicio().getTime()+
                                    "," + modelTratamento.getDataTermino().getTime()+
                                    "," //+ modelTratamento.getPaciente().getIdPaciente()+
                                        +0+
                                    "," //+ modelTratamento.getResponsavel().getIdResponsavel()+
                                        +0+
                                    "," + modelTratamento.getDoses()+
                                    ");";

            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (SQLiteException ex)
        {

        }
    }

    public static void update(ModelTratamento modelTratamento)
    {
        try
        {
            String query =  "UPDATE tratamento      " +
                            "SET nomeTratamento  = '" + modelTratamento.getNomeTratamento()                 + "'" +
                            "   ,dataInicio      =  " + modelTratamento.getDataInicio().getTime()           +
                            "   ,dataTermino     =  " + modelTratamento.getDataTermino().getTime()          +
                            "   ,idPaciente      =  " + modelTratamento.getPaciente().getIdPaciente()       +
                            "   ,idResponsavel   =  " + modelTratamento.getResponsavel().getIdResponsavel() +
                            "   ,doses =  " + modelTratamento.getDoses() +
                            " WHERE idTratamento =  " + modelTratamento.getIdTratamento()                   +";";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);

        }
        catch (SQLiteException ex)
        {

        }
    }
}
