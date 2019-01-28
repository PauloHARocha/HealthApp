package com.example.paulo.healthapp.Model;

/**
 * Created by paulorocha on 12/12/16.
 */

public class ModelLogin {
    private int idLogin;
    private int status;// 1 é logado e 0 é deslogado

    public ModelLogin(){}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }
}
