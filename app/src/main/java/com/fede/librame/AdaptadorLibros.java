package com.fede.librame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorLibros extends ArrayAdapter<StructListBooks> {
    StructListBooks[] datos;

    public AdaptadorLibros(Context context, StructListBooks[] datos){
        super(context, R.layout.items_books, datos);
        this.datos = datos;
    }

    public View getView(int posicion, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.items_books, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.lbl_title);
        lblTitulo.setText(datos[posicion].getTitulo());

        TextView lblDesc = (TextView)item.findViewById(R.id.lbl_short_description);
        lblDesc.setText(datos[posicion].getGenero());

        return(item);
    }
}
