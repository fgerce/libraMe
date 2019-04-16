package com.fede.librame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class newbook extends AppCompatActivity {

    public TextView txtISBN13, txtISBN10, txtAutor,txtEdicion, txtEncuadernacion, txtEditorial, txtFecha, txtPrecio;
    public EditText editISBN13, editISBN10, editAutor, editEdicion, editEncuadernacion, editEditorial, editFecha, editPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbook);

        txtISBN13 = (TextView)findViewById(R.id.txtISBN13);
        txtISBN10 = (TextView)findViewById(R.id.txtISBN10);
        txtAutor  = (TextView)findViewById(R.id.txtAutor);
        txtEdicion = (TextView)findViewById(R.id.txtEdicion);
        txtEncuadernacion = (TextView)findViewById(R.id.txtEncuadernacion);
        txtEditorial = (TextView)findViewById(R.id.txtEditorial);
        txtFecha = (TextView)findViewById(R.id.txtFecha);
        txtPrecio = (TextView)findViewById(R.id.txtPrecio);
        editISBN13 = (EditText)findViewById(R.id.editISBN13);
        editISBN10 = (EditText)findViewById(R.id.editISBN10);
        editAutor = (EditText)findViewById(R.id.editAutor);
        editEdicion = (EditText)findViewById(R.id.editEdicion);
        editEncuadernacion = (EditText)findViewById(R.id.editEncuadernacion);
        editEditorial = (EditText)findViewById(R.id.editEditorial);
        editFecha = (EditText)findViewById(R.id.editFecha);
        editPrecio = (EditText)findViewById(R.id.editPrecio);


    }
}
