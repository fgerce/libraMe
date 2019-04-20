package com.fede.librame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdaptadorLibros extends ArrayAdapter<StructListBooks> {
    StructListBooks[] datos;

    public AdaptadorLibros(Context context, StructListBooks[] datos){
        super(context, R.layout.items_books, datos);
        this.datos = datos;
    }

    public View getView(int posicion, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.items_books, null);

        TextView lblTitulo = item.findViewById(R.id.lbl_title);
        lblTitulo.setText(datos[posicion].getTitulo());

        TextView lblAutor = item.findViewById(R.id.lbl_autor);
        lblAutor.setText(datos[posicion].getAutor());

        TextView lblGenero = item.findViewById(R.id.lbl_genero);
        lblGenero.setText(datos[posicion].getGenero());


        return(item);
    }
}
