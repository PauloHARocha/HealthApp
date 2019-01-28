package com.example.paulo.healthapp.Model;


import java.util.Date;

public class ModelPaciente

{
    private int idPaciente;
    private String nomePaciente;
    private String nDeRegistroDaUnidadeSaude;
    private String cartaoNacionalDeSaude;
    private Date dataDeNascimento;
    private String telefone;
    private String endereco;
    private String tuberculose;
    private String responsavel;
    private int Peso;

    public ModelPaciente()
    {
        this.dataDeNascimento = new Date(0);
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getnDeRegistroDaUnidadeSaude() {
        return nDeRegistroDaUnidadeSaude;
    }

    public void setnDeRegistroDaUnidadeSaude(String nDeRegistroDaUnidadeSaude) {
        this.nDeRegistroDaUnidadeSaude = nDeRegistroDaUnidadeSaude;
    }

    public String getCartaoNacionalDeSaude() {
        return cartaoNacionalDeSaude;
    }

    public void setCartaoNacionalDeSaude(String cartaoNacionalDeSaude) {
        this.cartaoNacionalDeSaude = cartaoNacionalDeSaude;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTuberculose() {
        return tuberculose;
    }

    public void setTuberculose(String tuberculose) {
        this.tuberculose = tuberculose;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public int getPeso() {
        return Peso;
    }

    public void setPeso(int peso) {
        Peso = peso;
    }
}
