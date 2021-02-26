package com.ebveneto.models;

import retrofit.http.Part;

/**
 * Created by Asmita on 20-01-2017.
 */

public class Parente {
    private String parentela;
    private String parentelaName;
    private String cognome;
    private String nome;
    private String natoComune;
    private String natoData;
    private String natoProv;
    private String CF;

    public String getParentela() {
        return parentela;
    }

    public void setParentela(String parentela) {
        this.parentela = parentela;
    }

    @Override
    public String toString() {
        return parentela;
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

    public String getNatoComune() {
        return natoComune;
    }

    public void setNatoComune(String natoComune) {
        this.natoComune = natoComune;
    }

    public String getNatoData() {
        return natoData;
    }

    public void setNatoData(String natoData) {
        this.natoData = natoData;
    }

    public String getNatoProv() {
        return natoProv;
    }

    public void setNatoProv(String natoProv) {
        this.natoProv = natoProv;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public String getParentelaName() {
        return parentelaName;
    }

    public void setParentelaName(String parentelaName) {
        this.parentelaName = parentelaName;
    }
}
