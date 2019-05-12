package com.fede.librame.Fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.fede.librame.Activities.BookDetails;
import com.fede.librame.Activities.Settings;
import com.fede.librame.R;

public class Tab1Info extends Fragment {

    private BookDetails activity;
    private TextView txtTitulo, txtAutor, txtGenero, txtEdicion, txtCantidad, txtDate;
    public Tab1Info() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1_info, container, false);
        activity = (BookDetails) getActivity();
        txtTitulo = v.findViewById(R.id.txt_titulo);
        txtAutor = v.findViewById(R.id.txt_autor);
        txtGenero = v.findViewById(R.id.txt_genero);
        txtEdicion = v.findViewById(R.id.txt_edicion);
        txtCantidad = v.findViewById(R.id.txt_cantidad);
        txtDate = v.findViewById(R.id.txt_date);

        txtTitulo.setText(getString(R.string.Titulo) + ": " + activity.libroactual.getTitulo());
        txtAutor.setText(getString(R.string.Autor) + ": " + activity.libroactual.getAutor());
        txtGenero.setText(getString(R.string.Genero) + ": " + activity.libroactual.getGenero());
        txtEdicion.setText(getString(R.string.Edicion) + ": " + String.valueOf(activity.libroactual.getEdicion()));
        txtDate.setText(getString(R.string.Fecha) + ": " + activity.libroactual.getFechapublicacion());

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (pref.getBoolean("stock_mode", false) == true)
        {
            txtCantidad.setVisibility(View.VISIBLE);
            txtCantidad.setText(getString(R.string.Cantidad) + ": " + String.valueOf(activity.libroactual.getCantidad()));
        }

        return v;
    }


}
