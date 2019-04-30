package com.fede.librame.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fede.librame.Adapters.ViewPagerAdapter;
import com.fede.librame.Clases.StructListBooks;
import com.fede.librame.Fragments.Tab1Info;
import com.fede.librame.Fragments.Tab2Info;
import com.fede.librame.Fragments.Tab3Imagen;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;
import com.fede.librame.R;

import java.sql.Struct;

public class BookDetails extends AppCompatActivity {

    private Integer ID;
    private String usuario;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public SQLiteDatabase db;

    private String Titulo;
    private String Autor;
    private String Genero;
    private String Fecha;
    private Integer Paginas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        ID = getIntent().getExtras().getInt("ID", -1);
        usuario = getIntent().getExtras().getString("User", "");

        try {
            UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe", null, 1);
            db = usdbh.getReadableDatabase();
            if (db == null) {
                finish();
            }

            String[] campos = new String[] {"ID", "Titulo", "Genero", "Autor", "Fecha", "Paginas", "Usuario"};
            String[] args = new String[] {String.valueOf(ID), usuario};
            Cursor cursor = db.query("libros", campos, "ID=? AND Usuario=?", args, null, null, null);

            if(cursor.getCount() == 1) {

                Log.d("ALGO", cursor.getString(cursor.getColumnIndex("Titulo")));
                //Toast.makeText(this, cursor.getString(cursor.getColumnIndex("Titulo")), Toast.LENGTH_SHORT).show();
                /*Titulo = cursor.getString(cursor.getColumnIndex("Titulo"));
                Genero = cursor.getString(cursor.getColumnIndex("Genero"));
                Autor = cursor.getString(cursor.getColumnIndex("Autor"));
                Fecha = cursor.getString(cursor.getColumnIndex("Fecha"));
                Paginas = cursor.getInt(cursor.getColumnIndex("Paginas"));
                Log.d("Data", Titulo + Genero + Autor + Fecha + String.valueOf(Paginas));*/
            }
        }catch (Exception e)
        {
            Toast.makeText(this, "Error en carga de base de datos - Consulte servicio", Toast.LENGTH_SHORT).show();
        }


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
}