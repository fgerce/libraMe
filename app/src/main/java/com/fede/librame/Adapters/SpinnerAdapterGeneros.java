package com.fede.librame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fede.librame.R;

public class SpinnerAdapterGeneros extends ArrayAdapter<String> {
    public String[] cadena;

    public SpinnerAdapterGeneros(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.cadena = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View SpinnerXML =inflater.inflate(R.layout.spinner, parent, false);
        TextView label = SpinnerXML.findViewById(R.id.txtGenerosSpinner);
        label.setText(cadena[position]);

        return SpinnerXML;

    }
}
