package com.example.paulo.healthapp.Model;

import java.util.Calendar;
import java.util.Date;

public class ModelTratamento
{
    private int idTratamento;
    private String nomeTratamento;
    private Date dataInicio;
    private Date dataTermino;
    private ModelPaciente paciente;
    private ModelResponsavel responsavel;
    private int doses;

    public ModelTratamento()
    {
        Calendar datas = Calendar.getInstance();
        datas.setTime(new Date());
        datas.set(Calendar.HOUR_OF_DAY, 0);
        datas.set(Calendar.MINUTE, 0);
        datas.set(Calendar.SECOND, 0);
        datas.set(Calendar.MILLISECOND,0);
        this.dataInicio = datas.getTime();
        datas.set(Calendar.HOUR_OF_DAY, 23);
        datas.set(Calendar.MINUTE,59);
        datas.set(Calendar.SECOND,59);
        datas.set(Calendar.MILLISECOND,0);
        this.dataTermino = datas.getTime();
    }

    public int getIdTratamento() {
        return idTratamento;
    }

    public void setIdTratamento(int idTratamento) {
        this.idTratamento = idTratamento;
    }

    public String getNomeTratamento() {
        return nomeTratamento;
    }

    public void setNomeTratamento(String nomeTratamento) {
        this.nomeTratamento = nomeTratamento;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public ModelPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(ModelPaciente paciente) {
        this.paciente = paciente;
    }

    public ModelResponsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ModelResponsavel responsavel) {
        this.responsavel = responsavel;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int dose) {
        this.doses = dose;
    }
}
