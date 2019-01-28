package com.example.paulo.healthapp.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.example.paulo.healthapp.Dao.Dao;
import com.example.paulo.healthapp.Model.ModelLogin;

/**
 * Created by paulorocha on 12/12/16.
 */

public class ServiceLogin {

    public static void insert(ModelLogin modelLogin)
    {
        try
        {
            String query =  "INSERT INTO login (status) " +
                    "VALUES ("   + modelLogin.getStatus() + ");";

            Dao.getInstance(null).getWritableDatabase().execSQL(query);
        }
        catch (SQLiteException ex)
        {

        }
    }

    public static void update(ModelLogin modelLogin)
    {
        try
        {
            String query =  "UPDATE login     " +
                    "SET status = " + modelLogin.getStatus()  +
                    " WHERE idLogin = " + modelLogin.getIdLogin()          +";";
            Dao.getInstance(null).getWritableDatabase().execSQL(query);

        }
        catch (SQLiteException ex)
        {

        }
    }

    public static ModelLogin getLoginStatus(int id)
    {
        try
        {
            ModelLogin modelLogin = new ModelLogin();
            String query;

            query = "SELECT idLogin, status " +
                    "FROM login                                              " +
                    "WHERE idLogin =                                         " + id;

            Cursor cursor = Dao.getInstance(null).getReadableDatabase().rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                modelLogin.setIdLogin(cursor.getInt(0));
                modelLogin.setStatus(cursor.getInt(1));

            }
            return modelLogin;
        }
        catch (SQLiteException ex)
        {
            return null;
        }
    }
}
