package com.fede.librame.Clases;


import android.net.Uri;


public class StructListBooks {

    private Integer ID;
    private String ISBN13;
    private String ISBN10;
    private String titulo;
    private String autor;
    private String genero;
    private String descripcion;
    private Integer edicion;
    private String encuadernacion;
    private String editorial;
    private String fechapublicacion;
    private Integer precio;
    private Uri rutaportada;
    private String usuario;
    private Integer paginas;
    private String estado;
    private Integer cantidad;

    public StructListBooks(Integer ID, String ISBN13, String ISBN10, String titulo, String autor, String genero, String descripcion, Integer edicion, String encuadernacion,
                           String editorial, String fechapublicacion, Integer precio, Uri rutaportada, String usuario, Integer paginas, String estado, Integer cantidad) {
        this.ID = ID;
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
        this.rutaportada = rutaportada;
        this.usuario = usuario;
        this.paginas = paginas;
        this.estado = estado;
        this.cantidad = cantidad;
    }

    public StructListBooks(Integer ID, String titulo, String genero, String autor, String fecha, Integer paginas, Uri rutaportada)
    {
        this.ID = ID;
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.fechapublicacion = fecha;
        this.paginas = paginas;
        this.rutaportada = rutaportada;
    }

    public StructListBooks(Integer ID, String titulo, String genero, String autor, String fecha, Integer paginas)
    {
        this.ID = ID;
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.fechapublicacion = fecha;
        this.paginas = paginas;
    }

    public StructListBooks(String titulo, String genero, String autor, String fecha, Integer paginas)
    {
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.fechapublicacion = fecha;
        this.paginas = paginas;
    }

    //Getters
    public String getAutor(){
        return autor;
    }

    public String getEncuadernacion(){
        return encuadernacion;
    }

    public String getISBN13(){
        return ISBN13;
    }

    public String getISBN10(){
        return ISBN10;
    }

    public Uri getRutaportada() {
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

    public Integer getPaginas() {
        return paginas;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getID() {
        return ID;
    }

    //Setters
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEdicion(Integer edicion) {
        this.edicion = edicion;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setEncuadernacion(String encuadernacion) {
        this.encuadernacion = encuadernacion;
    }

    public void setFechapublicacion(String fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setISBN10(String ISBN10) {
        this.ISBN10 = ISBN10;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public void setRutaportada(Uri rutaportada) {
        this.rutaportada = rutaportada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
