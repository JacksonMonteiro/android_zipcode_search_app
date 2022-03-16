package com.example.consultacep.model;

<<<<<<< HEAD:app/src/main/java/com/example/consultacep/model/TrackingCode.java
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
=======
public class trackingCode {
    private String code;
    private String desc;
    private String city;
    private String uf;
>>>>>>> parent of 0a08ed1 (Tracking Service Generator class):app/src/main/java/com/example/consultacep/model/trackingCode.java

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
    }
}
