package com.fede.librame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.fede.librame.Adapters.ViewPagerAdapter;
import com.fede.librame.Clases.StructListBooks;
import com.fede.librame.Fragments.Tab1Info;
import com.fede.librame.Fragments.Tab2Info;
import com.fede.librame.Fragments.Tab3Imagen;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;
import com.fede.librame.R;

import java.net.URI;
import java.sql.Struct;

public class BookDetails extends AppCompatActivity {

    public StructListBooks libroactual; //Este objeto es lo que leen los fragments *TABS*

    private Integer ID;
    private String usuario;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SQLiteDatabase db;
    private Toolbar toolbar;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        ID = getIntent().getExtras().getInt("ID", -1);
        usuario = getIntent().getExtras().getString("User", "");

        layout = findViewById(R.id.detailsLayout);
        refreshColor(layout);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detalles");
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primaryTextColor));
        toolbar.inflateMenu(R.menu.toolbardetails);

        try {
            UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe", null, 1);
            db = usdbh.getReadableDatabase();
            if (db == null) {
                finish();
            }
            String ISBN13;
            String ISBN10;
            String titulo;
            String autor;
            String genero;
            String descripcion;
            Integer edicion;
            String encuadernacion;
            String editorial;
            String fechapublicacion;
            Integer precio;
            Uri rutaportada;
            Integer paginas;
            String estado;
            Integer cantidad;

            String[] campos = new String[] {"ID", "ISBN13", "ISBN10", "Titulo", "Autor", "Genero", "Descripcion",
                                            "Edicion", "Encuadernacion", "Editorial", "Fecha", "Precio",
                                            "Paginas", "Rutaportada", "Estado", "Cantidad", "Usuario"};
            String[] args = new String[] {String.valueOf(ID), usuario};
            Cursor cursor = db.query("libros", campos, "ID=? AND Usuario=?", args, null, null, null);

            if(cursor.getCount()==1) {
                if (cursor.moveToFirst()) {
                    //Loop through the table rows
                    do {
                        ISBN13 = cursor.getString(cursor.getColumnIndex("ISBN13"));
                        ISBN10 = cursor.getString(cursor.getColumnIndex("ISBN10"));
                        titulo = cursor.getString(cursor.getColumnIndex("Titulo"));
                        toolbar.setTitle(titulo);
                        autor = cursor.getString(cursor.getColumnIndex("Autor"));
                        genero = cursor.getString(cursor.getColumnIndex("Genero"));
                        descripcion = cursor.getString(cursor.getColumnIndex("Descripcion"));
                        edicion = cursor.getInt(cursor.getColumnIndex("Edicion"));
                        encuadernacion = cursor.getString(cursor.getColumnIndex("Encuadernacion"));
                        editorial = cursor.getString(cursor.getColumnIndex("Editorial"));
                        fechapublicacion = cursor.getString(cursor.getColumnIndex("Fecha"));
                        precio = cursor.getInt(cursor.getColumnIndex("Precio"));
                        paginas = cursor.getInt(cursor.getColumnIndex("Paginas"));
                        rutaportada = Uri.parse(cursor.getString(cursor.getColumnIndex("Rutaportada")));
                        estado = cursor.getString(cursor.getColumnIndex("Estado"));
                        cantidad = cursor.getInt(cursor.getColumnIndex("Cantidad"));
                        libroactual = new StructListBooks(
                                                ID,
                                                ISBN13,
                                                ISBN10,
                                                titulo,
                                                autor,
                                                genero,
                                                descripcion,
                                                edicion,
                                                encuadernacion,
                                                editorial,
                                                fechapublicacion,
                                                precio,
                                                rutaportada,
                                                usuario,
                                                paginas,
                                                estado,
                                                cantidad);

                    } while (cursor.moveToNext());
                }
            }
        }catch (Exception e)
        {
            Toast.makeText(this, "Error en carga de base de datos - Consulte tecnico", Toast.LENGTH_SHORT).show();
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        new AlertDialog.Builder(BookDetails.this)
                                .setTitle("Eliminar")
                                .setMessage("¿Está seguro que desea eliminar este libro?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.delete("libros", "id="+ ID, null);
                                        finish();
                                    }
                                })
                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return true;

                    default:
                        return false;
                }
            }
        });

        //Podria comprobar que el usuario sea el correspondiente al libro...

        tabLayout = (TabLayout) findViewById(R.id.infotabs);
        viewPager = (ViewPager) findViewById(R.id.pagertabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager)
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager()); //Usar Child!
        adapter.addFragment(new Tab1Info(),"Informacion");
        adapter.addFragment(new Tab2Info(),"Detalles");
        adapter.addFragment(new Tab3Imagen(),"Portada");
        viewPager.setAdapter(adapter);
    }

    public void refreshColor(ConstraintLayout layout) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String color = pref.getString("back_color", "0");
        if (!(color.equals("0")))
        {
            layout.setBackgroundColor(Color.parseColor(color));
        }
        else
        {
            layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.primaryColor));
        }
    }

}