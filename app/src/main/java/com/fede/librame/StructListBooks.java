package com.fede.librame;


import java.net.URI;
import java.util.Date;

public class StructListBooks {

    private Integer ISBN13;
    private Integer ISBN10;
    private String autor;
    private Integer edicion;
    private String encuadernacion;
    private Date fechapublicacion;
    private Integer precio;
    private String titulo;
    private String genero;
    private String descripcion;
    private URI rutaportada;

    public StructListBooks(Integer ISBN13, Integer ISBN10, String autor, Integer edicion, String encuadernacion, Date fechapublicacion, Integer precio, String titulo, String genero, String descripcion, URI rutaportada) {
        this.ISBN13 = ISBN13;
        this.ISBN10 = ISBN10;
        this.autor = autor;
        this.edicion = edicion;
        this.encuadernacion = encuadernacion;
        this.fechapublicacion = fechapublicacion;
        this.precio = precio;
        this.titulo=titulo;
        this.genero=genero;
        this.descripcion=descripcion;
        this.rutaportada=rutaportada;
    }

    public StructListBooks(String titulo, String desc, String genero,URI rutaimagen) {
        this.titulo=titulo;
        this.rutaportada=rutaimagen;
        this.genero=genero;
        this.descripcion=desc;
    }

    public StructListBooks(String titulo, String desc, String genero) {
        this.titulo=titulo;
        this.genero=genero;
        this.descripcion=desc;
    }

    public StructListBooks(String titulo, String genero) {
        this.titulo=titulo;
        this.genero=genero;
    }

    //Getters
    public String getAutor(){
        return autor;
    }

    public String getEncuadernacion(){
        return encuadernacion;
    }

    public Integer getISBN13(){
        return ISBN13;
    }

    public Integer getISBN10(){
        return ISBN10;
    }

    public URI getRutaportada() {
        return rutaportada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getPrecio() {
        return precio;
    }

    public Integer getEdicion() {
        return edicion;
    }

    public Date getFechapublicacion() {
        return fechapublicacion;
    }

    public String getGenero(){return genero;}

    public String getDescripcion(){return descripcion;}
}
