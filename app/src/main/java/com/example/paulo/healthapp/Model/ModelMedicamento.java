package com.example.paulo.healthapp.Model;

import java.util.Date;

public class ModelMedicamento


{
    private int idMedicamento;
    private String nomeMedicamento;
    private int qtdMedicamento;
    private int qtdTomados;
    private String corMedicamento;
    private Date horaMedicamento;
    private Date dataInicial;
    private Date dataFinal;
    private ModelTratamento tratamento;
    private int possuiHistorico;

    public ModelMedicamento()
    {
        this.horaMedicamento = new Date(0);
        this.dataInicial = new Date(0);
        this.dataFinal = new Date(0);
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public int getQtdMedicamento() {
        return qtdMedicamento;
    }

    public void setQtdMedicamento(int qtdMedicamento) {
        this.qtdMedicamento = qtdMedicamento;
    }

    public String getCorMedicamento() {
        return corMedicamento;
    }

    public void setCorMedicamento(String corMedicamento) {
        this.corMedicamento = corMedicamento;
    }

    public Date getHoraMedicamento() {
        return horaMedicamento;
    }

    public void setHoraMedicamento(Date horaMedicamento) {
        this.horaMedicamento = horaMedicamento;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public ModelTratamento getTratamento() {
        return tratamento;
    }

    public void setTratamento(ModelTratamento tratamento) {
        this.tratamento = tratamento;
    }

    public int getQtdTomados() {
        return qtdTomados;
    }

    public void setQtdTomados(int qtdTomados) {
        this.qtdTomados = qtdTomados;
    }

    public int getPossuiHistorico() {
        return possuiHistorico;
    }

    public void setPossuiHistorico(int possuiHistorico) {
        this.possuiHistorico = possuiHistorico;
    }
}
