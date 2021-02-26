package com.ebveneto.models;

/**
 * Created by Asmita on 05-01-2017.
 */

public class ProfileData {
    private int idDIP;
    private String tipo;
    private String cognome;
    private String nome;
    private String sesso;
    private String dtNascita;
    private String cf;

    public int getIdDIP() {
        return idDIP;
    }

    public void setIdDIP(int idDIP) {
        this.idDIP = idDIP;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getDtNascita() {
        return dtNascita;
    }

    public void setDtNascita(String dtNascita) {
        this.dtNascita = dtNascita;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }
}
