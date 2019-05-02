package com.fede.librame.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fede.librame.Activities.BookDetails;
import com.fede.librame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Info extends Fragment {

    private BookDetails activity;
    public TextView txtISBN13, txtISBN10, txtDescripcion, txtEncuadernacion;

    public Tab2Info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab2_info, container, false);
        activity = (BookDetails)getActivity();
        txtISBN13 = v.findViewById(R.id.txt_ISBN13);
        txtISBN10 = v.findViewById(R.id.txt_ISBN10);
        txtDescripcion = v.findViewById(R.id.txt_Descripcion);
        txtEncuadernacion = v.findViewById(R.id.txt_Encuadernacion);

        txtISBN13.setText(getString(R.string.ISBN13) + ": " + activity.libroactual.getISBN13());
        txtISBN10.setText(getString(R.string.ISBN10) + ": " + activity.libroactual.getISBN10());
        if(activity.libroactual.getDescripcion().equals(""))
        {
            txtDescripcion.setText("Descripcion faltante");
        }
        else {
            txtDescripcion.setText(activity.libroactual.getDescripcion());
        }
        txtDescripcion.setMovementMethod(new ScrollingMovementMethod());
        txtEncuadernacion.setText(getString(R.string.Encuadernacion) + ": " + activity.libroactual.getEncuadernacion());

        return v;
    }

}
