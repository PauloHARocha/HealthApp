package com.example.paulo.healthapp.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.example.paulo.healthapp.Dao.Dao;
import com.example.paulo.healthapp.Model.ModelResponsavel;

import java.util.ArrayList;
import java.util.List;

public class ServiceResponsavel
{

    public static ModelResponsavel getById(int id)
    {
        try
        {
            ModelResponsavel modelResponsavel = new ModelResponsavel();;
            String query;

            query = "SELECT idResponsavel, nomeResponsavel, cpf, endereco, telefone " +
                    "FROM responsavel                                      " +
                    "WHERE idResponsavel =                                 " + id;

            Cursor cursor = null;
            cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                modelResponsavel.setIdResponsavel(cursor.getInt(0));
                modelResponsavel.setNomeResponsavel(cursor.getString(1));
                modelResponsavel.setCpf(cursor.getString(2));
                modelResponsavel.setEndereco(cursor.getString(3));
                modelResponsavel.setTelefone(cursor.getString(4));
            }

            return modelResponsavel;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static List<ModelResponsavel> getList()
    {
        try
        {
            ArrayList<ModelResponsavel> lstModelResponsavel = new ArrayList<ModelResponsavel>();
            String query;

            query = "SELECT idResponsavel, nomeResponsavel, cpf, endereco , telefone " +
                    "FROM responsavel;";


            Cursor cursor = null;
            cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelResponsavel modelResponsavel = new ModelResponsavel();

                    modelResponsavel.setIdResponsavel(cursor.getInt(0));
                    modelResponsavel.setNomeResponsavel(cursor.getString(1));
                    modelResponsavel.setCpf(cursor.getString(2));
                    modelResponsavel.setEndereco(cursor.getString(3));
                    modelResponsavel.setTelefone(cursor.getString(4));

                    lstModelResponsavel.add(modelResponsavel);

                } while (cursor.moveToNext());
            }

            return lstModelResponsavel;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static void insert(ModelResponsavel modelResponsavel)
    {
        try
        {
            String query =  "INSERT INTO responsavel (nomeResponsavel, cpf, endereco, telefone) " +
                            "VALUES ('"   + modelResponsavel.getNomeResponsavel() + "'" +
                                      ",'"+ modelResponsavel.getCpf()             + "'" +
                                      ",'"+ modelResponsavel.getEndereco()        + "'" +
                                      ",'"+ modelResponsavel.getTelefone()        + "'" +
                                      ");";

            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (SQLiteException ex)
        {

        }
    }

    public static void update(ModelResponsavel modelResponsavel)
    {
        try
        {
            String query =  "UPDATE responsavel           " +
                            "SET  nomeResponsavel =   '"+ modelResponsavel.getNomeResponsavel()+"'"+
                            "    ,cpf             =   '"+ modelResponsavel.getCpf()            +"'"+
                            "    ,endereco        =   '"+ modelResponsavel.getEndereco()       +"'"+
                            "    ,telefone        =   '"+ modelResponsavel.getTelefone()       +"'"+
                            " WHERE idResponsavel =    "+ modelResponsavel.getIdResponsavel()  +";";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (Exception ex)
        {

        }
    }
}
