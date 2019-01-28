package com.example.paulo.healthapp.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import com.example.paulo.healthapp.Dao.Dao;
import com.example.paulo.healthapp.Model.ModelPaciente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicePaciente
{

    public static ModelPaciente getById(int id)
    {
        try
        {
            ModelPaciente modelPaciente = new ModelPaciente();
            String query;

            query = "SELECT idPaciente, nomePaciente, nomeResponsavel, nDeRegistroDaUnidadeSaude, cartaoNacionalDeSaude, dataDeNascimento, telefone, endereco, tuberculose, peso " +
                    "FROM paciente                                    " +
                    "WHERE idPaciente =                               " +id;

            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToNext())
            {
                modelPaciente.setIdPaciente(cursor.getInt(0));
                modelPaciente.setNomePaciente(cursor.getString(1));
                modelPaciente.setResponsavel(cursor.getString(2));
                modelPaciente.setnDeRegistroDaUnidadeSaude(cursor.getString(3));
                modelPaciente.setCartaoNacionalDeSaude(cursor.getString(4));
                modelPaciente.setDataDeNascimento(new Date(cursor.getLong(5)));
                modelPaciente.setTelefone(cursor.getString(6));
                modelPaciente.setEndereco(cursor.getString(7));
                modelPaciente.setTuberculose(cursor.getString(8));
                modelPaciente.setPeso(cursor.getInt(9));
            }
            return modelPaciente;
        }
        catch (SQLiteException ex)
        {
            return null;
        }
    }

    public static List<ModelPaciente> getList()
    {
        try
        {
            ArrayList<ModelPaciente> lstModelPaciente = new ArrayList<ModelPaciente>();
            String query;

            query = "SELECT idPaciente, nomePaciente, nomeResponsavel, nDeRegistroDaUnidadeSaude, cartaoNacionalDeSaude, dataDeNascimento, telefone, endereco, tuberculose, peso " +
                    "FROM paciente;";


            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    ModelPaciente modelPaciente = new ModelPaciente();

                    modelPaciente.setIdPaciente(cursor.getInt(0));
                    modelPaciente.setNomePaciente(cursor.getString(1));
                    modelPaciente.setResponsavel(cursor.getString(2));
                    modelPaciente.setnDeRegistroDaUnidadeSaude(cursor.getString(3));
                    modelPaciente.setCartaoNacionalDeSaude(cursor.getString(4));
                    modelPaciente.setDataDeNascimento(new Date(cursor.getLong(5)));
                    modelPaciente.setTelefone(cursor.getString(6));
                    modelPaciente.setEndereco(cursor.getString(7));
                    modelPaciente.setTuberculose(cursor.getString(8));
                    modelPaciente.setPeso(cursor.getInt(9));

                    lstModelPaciente.add(modelPaciente);

                } while (cursor.moveToNext());
            }

            return lstModelPaciente;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static void insert(ModelPaciente modelPaciente)
    {
        try
        {
             String query =  "INSERT INTO paciente (idPaciente , nomePaciente, nomeResponsavel, nDeRegistroDaUnidadeSaude, cartaoNacionalDeSaude, dataDeNascimento, telefone, endereco, tuberculose, peso) " +
                            "VALUES (" + 1 +
                                      ", '"+ modelPaciente.getNomePaciente()               + "'" +
                                      ", '"+ modelPaciente.getResponsavel()               + "'" +
                                      ",'"+ modelPaciente.getnDeRegistroDaUnidadeSaude()  + "'" +
                                      ",'"+ modelPaciente.getCartaoNacionalDeSaude()      + "'" +
                                      "," + modelPaciente.getDataDeNascimento().getTime() +
                                      ",'"+ modelPaciente.getTelefone()                   + "'" +
                                      ",'"+ modelPaciente.getEndereco()                   + "'" +
                                      ",'"+ modelPaciente.getTuberculose()                + "'" +
                                      "," + modelPaciente.getPeso() +
                                      ");";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (Exception ex)
        {
            Log.e("erro", ex.getMessage());
        }
    }

    public static void update(ModelPaciente modelPaciente)
    {
        try
        {
            String query =  "UPDATE paciente           " +
                            "SET nomePaciente              = '" + modelPaciente.getNomePaciente()               + "'" +
                            "   ,nomeResponsavel           = '" + modelPaciente.getResponsavel()                + "'" +
                            "   ,nDeRegistroDaUnidadeSaude = '" + modelPaciente.getnDeRegistroDaUnidadeSaude()  + "'" +
                            "   ,cartaoNacionalDeSaude     = '" + modelPaciente.getCartaoNacionalDeSaude()      + "'" +
                            "   ,dataDeNascimento          =  " + modelPaciente.getDataDeNascimento().getTime() +
                            "   ,telefone                  = '" + modelPaciente.getTelefone()                   + "'" +
                            "   ,endereco                  = '" + modelPaciente.getEndereco()                   + "'" +
                            "   ,tuberculose               = '" + modelPaciente.getTuberculose()                + "'" +
                            "   ,peso          =  " + modelPaciente.getPeso() +
                            " WHERE idPaciente              =  " + modelPaciente.getIdPaciente()                +";";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);

        }
        catch (SQLiteException ex)
        {

        }
    }
}
