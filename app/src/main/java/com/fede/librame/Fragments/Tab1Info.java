package com.fede.librame.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fede.librame.Activities.BookDetails;
import com.fede.librame.R;

public class Tab1Info extends Fragment {

    private BookDetails activity;
    public TextView txtTitulo, txtAutor, txtGenero, txtEdicion;

    public Tab1Info() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1_info, container, false);
        activity = (BookDetails)getActivity();
        txtTitulo = v.findViewById(R.id.txt_titulo);
        txtAutor = v.findViewById(R.id.txt_autor);
        txtGenero = v.findViewById(R.id.txt_genero);
        txtEdicion = v.findViewById(R.id.txt_edicion);

        txtTitulo.setText(getString(R.string.Titulo) + ": " + activity.libroactual.getTitulo());
        txtAutor.setText(getString(R.string.Autor) + ": " + activity.libroactual.getAutor());
        txtGenero.setText(getString(R.string.Genero) + ": " + activity.libroactual.getGenero());
        txtEdicion.setText(getString(R.string.Edicion) + ": " + String.valueOf(activity.libroactual.getEdicion()));

        return v;
    }

}
