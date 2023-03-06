package com.example.securaudit.models;

public class CategorieFrais {

    int idCategorieFrais;

    String nameCategorieFrais;

    public CategorieFrais(int idCategorieFrais, String nameCategorieFrais) {
        this.idCategorieFrais = idCategorieFrais;
        this.nameCategorieFrais = nameCategorieFrais;
    }

    public int getIdCategorieFrais() {
        return idCategorieFrais;
    }

    public void setIdCategorieFrais(int idCategorieFrais) {
        this.idCategorieFrais = idCategorieFrais;
    }

    public String getNameCategorieFrais() {
        return nameCategorieFrais;
    }

    public void setNameCategorieFrais(String nameCategorieFrais) {
        this.nameCategorieFrais = nameCategorieFrais;
    }
}
