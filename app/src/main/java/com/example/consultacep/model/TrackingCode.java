package com.example.consultacep.model;

<<<<<<< HEAD
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
=======
import java.lang.reflect.Array;

public class TrackingCode {
    private String codigo;
    private String servico;
    private String host;
    private int quantidade;
    private Array eventos;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Array getEventos() {
        return eventos;
    }

    public void setEventos(Array eventos) {
        this.eventos = eventos;
>>>>>>> b040973b95a29fa8941c1513bea2a190bf3a2349
    }
}
