package com.fede.librame;


import java.net.URI;

public class StructListBooks {
    private String titulo;
    private URI rutaportada;
    private String genero;
    private String descripcion;

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

    public String getTitulo() {
        return titulo;
    }

    public URI getPortada() {
        return rutaportada;
    }

    public String getGenero(){return genero;}

    public String getDescripcion(){return descripcion;}
}
