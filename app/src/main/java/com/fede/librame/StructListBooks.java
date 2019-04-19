package com.fede.librame;


import java.net.URI;
import java.util.Date;

public class StructListBooks {

    private Integer ISBN13;
    private Integer ISBN10;
    private String titulo;
    private String autor;
    private String genero;
    private String descripcion;
    private Integer edicion;
    private String encuadernacion;
    private String editorial;
    private Date fechapublicacion;
    private Integer precio;
    private URI rutaportada;


    public StructListBooks(Integer ISBN13, Integer ISBN10, String titulo, String autor, String genero, String descripcion, Integer edicion, String encuadernacion,
                           String editorial, Date fechapublicacion, Integer precio, URI rutaportada) {
        this.ISBN13 = ISBN13;
        this.ISBN10 = ISBN10;
        this.titulo=titulo;
        this.autor = autor;
        this.genero=genero;
        this.descripcion=descripcion;
        this.edicion = edicion;
        this.encuadernacion = encuadernacion;
        this.editorial = editorial;
        this.fechapublicacion = fechapublicacion;
        this.precio = precio;
        this.rutaportada=rutaportada;
    }

    public StructListBooks(Integer ISBN13, Integer ISBN10, String titulo, String autor, String genero, String descripcion, Integer edicion, String encuadernacion,
                           String editorial, Date fechapublicacion, Integer precio) {
        this.ISBN13 = ISBN13;
        this.ISBN10 = ISBN10;
        this.titulo=titulo;
        this.autor = autor;
        this.genero=genero;
        this.descripcion=descripcion;
        this.edicion = edicion;
        this.encuadernacion = encuadernacion;
        this.editorial = editorial;
        this.fechapublicacion = fechapublicacion;
        this.precio = precio;
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

    public String getEditorial() {
        return editorial;
    }
}
