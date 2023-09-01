package org.acestream.engine.client.example;

public class AcestreamElement {
    private String enlace;
    private String imagen;
    private String imagen_fondo;
    private boolean estado;

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen_fondo() {
        return imagen_fondo;
    }

    public void setImagen_fondo(String imagen_fondo) {
        this.imagen_fondo = imagen_fondo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private int orden;
    private String _id;
    private String nombre;

    @Override
    public String toString() {
        return "Resultado{" +
                "enlace='" + enlace + '\'' +
                ", imagen='" + imagen + '\'' +
                ", imagen_fondo='" + imagen_fondo + '\'' +
                ", estado=" + estado +
                ", orden=" + orden +
                ", _id='" + _id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
