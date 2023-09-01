package org.acestream.engine.client.example;

public class ChannelModel {
    private String nombre;
    private String enlace;

    public ChannelModel(){
        nombre = null;
        enlace = null;
    }

    public ChannelModel(String nombre, String enlace ){
        this.nombre = nombre;
        this.enlace= enlace;
    }
    public String getNombre() {
        return nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
