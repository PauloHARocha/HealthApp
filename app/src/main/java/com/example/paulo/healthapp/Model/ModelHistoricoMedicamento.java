package com.example.paulo.healthapp.Model;

import java.util.Date;

public class ModelHistoricoMedicamento

{
    private int idHistoricoMedicamento;
    private Date horaPrevista;
    private Date horaReal;
    private int qtdMedicamento;
    private int qtdTomados;
    private ModelMedicamento medicamento;
    private int confirmado;

    public ModelHistoricoMedicamento()
    {
        this.horaPrevista = new Date(0);
        this.horaReal = new Date(0);
    }

    public int getIdHistoricoMedicamento() {
        return idHistoricoMedicamento;
    }

    public void setIdHistoricoMedicamento(int idHistoricoMedicamento) {
        this.idHistoricoMedicamento = idHistoricoMedicamento;
    }

    public Date getHoraPrevista() {
        return horaPrevista;
    }

    public void setHoraPrevista(Date horaPrevista) {
        this.horaPrevista = horaPrevista;
    }

    public Date getHoraReal() {
        return horaReal;
    }

    public void setHoraReal(Date horaReal) {
        this.horaReal = horaReal;
    }

    public int getQtdTomados() {
        return qtdTomados;
    }

    public void setQtdTomados(int qtdTomados) {
        this.qtdTomados = qtdTomados;
    }

    public int getQtdMedicamento() {
        return qtdMedicamento;
    }

    public void setQtdMedicamento(int qtdMedicamento) {
        this.qtdMedicamento = qtdMedicamento;
    }

    public ModelMedicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(ModelMedicamento medicamento) {
        this.medicamento = medicamento;
    }

    public int getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(int confirmado) {
        this.confirmado = confirmado;
    }
}
