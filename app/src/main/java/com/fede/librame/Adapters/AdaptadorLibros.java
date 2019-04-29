package com.fede.librame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fede.librame.R;
import com.fede.librame.Clases.StructListBooks;

import java.util.List;

public class AdaptadorLibros extends ArrayAdapter<StructListBooks> {
   List<StructListBooks> datos;

    public AdaptadorLibros(Context context,  List <StructListBooks> datos){
        super(context, R.layout.items_books);
        this.datos = datos;
    }

    public View getView(int posicion, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.items_books, null);

        TextView lblTitulo = item.findViewById(R.id.lbl_title);
        lblTitulo.setText(datos.get(posicion).getTitulo());

        TextView lblAutor = item.findViewById(R.id.lbl_autor);
        lblAutor.setText(datos.get(posicion).getAutor());

        TextView lblGenero = item.findViewById(R.id.lbl_genero);
        lblGenero.setText(datos.get(posicion).getGenero());

        TextView lblFecha = item.findViewById(R.id.lbl_fecha);
        lblFecha.setText(datos.get(posicion).getFechapublicacion());

        TextView lblPaginas = item.findViewById(R.id.lbl_paginas);
        lblPaginas.setText(datos.get(posicion).getPaginas());

        return(item);
    }
}
