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
    private String fechapublicacion;
    private Integer precio;
    private URI rutaportada;
    private String usuario;
    private String paginas;


    public StructListBooks(Integer ISBN13, Integer ISBN10, String titulo, String autor, String genero, String descripcion, Integer edicion, String encuadernacion,
                           String editorial, String fechapublicacion, Integer precio, URI rutaportada, String usuario) {
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
        this.usuario=usuario;
    }

    public StructListBooks(Integer ISBN13, Integer ISBN10, String titulo, String autor, String genero, String descripcion, Integer edicion, String encuadernacion,
                           String editorial, String fechapublicacion, Integer precio, String usuario) {
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
        this.usuario=usuario;
    }

    public StructListBooks(String titulo, String genero, String autor, String fecha, String paginas)
    {
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.fechapublicacion = fecha;
        this.paginas = paginas;
    }

    public StructListBooks(String titulo, String genero, String autor)
    {
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
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

    public String getFechapublicacion() {
        return fechapublicacion;
    }

    public String getGenero(){return genero;}

    public String getDescripcion(){return descripcion;}

    public String getEditorial() {
        return editorial;
    }

    public String getUsuario()
    {
        return usuario;
    }

    public String getPaginas() {
        return paginas;
    }
}
