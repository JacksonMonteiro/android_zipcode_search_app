package com.example.consultacep.model;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TrackingCode {
    private ArrayList<Object> objetos;
    private int quantidade;
    private String resultado;
    private String versao;

    public ArrayList<Object> getObjetos() {
        return objetos;
    }

    public void setObjetos(ArrayList<Object> objetos) {
        this.objetos = objetos;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    @NonNull
    @Override
    public String toString() {
        return "Track code [objeto=" + this.objetos + ", quantidade=" + quantidade + ", resultado=" + resultado + ", versao=" + versao;
    }
}
