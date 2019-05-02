package com.fede.librame.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.fede.librame.Adapters.AdaptadorLibros;
import com.fede.librame.R;
import com.fede.librame.Clases.StructListBooks;
import com.fede.librame.Clases.fetchBooks;

import java.util.ArrayList;
import java.util.List;

public class listadoBusqueda extends AppCompatActivity {

    public ListView listBooks;
    public fetchBooks busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_busqueda);

        listBooks = findViewById(R.id.listBooks);
        busqueda = new fetchBooks(getIntent().getExtras().getString("bookID", ""), this);

    }

    public void RefreshList() {
        List<StructListBooks> data = new ArrayList<StructListBooks>();

            data.add(new StructListBooks("Titulo: " + busqueda.getTitulo(),
                                "Genero" + " ",
                                "Autor: " + busqueda.getAutor(),
                                "Fecha: " + busqueda.getFechapublicacion(),
                                 busqueda.getPaginas()
                    ));

    AdaptadorLibros adaptador =
        new AdaptadorLibros(this, data);
    listBooks.setAdapter(adaptador);
    }
}