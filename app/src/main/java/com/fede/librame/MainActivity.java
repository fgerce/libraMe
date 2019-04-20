package com.fede.librame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView listBooks;
    public Toolbar toolbar;
    public String userlog;
    public FloatingActionButton AddButton, RefreshButton;
    public SQLiteDatabase db;

    static final int PICK_NEW_BOOK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe",null,1);
        db = usdbh.getWritableDatabase();
        if(db == null)
        {
            finish();
        }

        AddButton = findViewById(R.id.floatingAddButton);
        RefreshButton = findViewById(R.id.floatingRefreshButton);
        toolbar = findViewById(R.id.toolbar);
        userlog = getIntent().getExtras().getString("User"," ");
        toolbar.setTitle("Hola " + userlog);
        toolbar.setSubtitle(getResources().getString(R.string.menuseleccion));
        toolbar.inflateMenu(R.menu.toolbar_menu);
        listBooks = findViewById(R.id.listBooks);

        try{
            RefreshList();
        }catch (Exception e)
        {
            Toast.makeText(this, "Error cargando datos", Toast.LENGTH_SHORT).show();
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_main_setting:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        return false;
                }
            }
        });

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewBook = new Intent().setClass(MainActivity.this, newbook.class);
                toNewBook.putExtra("User", userlog.toString());
                startActivityForResult(toNewBook,PICK_NEW_BOOK);
            }
        });

        RefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    RefreshList();
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Error cargando datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_NEW_BOOK) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try{
                    RefreshList();
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Error cargando datos", Toast.LENGTH_SHORT).show();
                }
            }
            else if(resultCode == newbook.CANCELADO)
            {

            }
            else
            {
                Toast.makeText(this, "Solicitar tecnico, problema en newbook", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void RefreshList()
    {
        /*
        String allQuery = "SELECT * FROM " + "libros";
        Cursor cursor = db.rawQuery(allQuery, null);
        */
        String[] campos = new String[] {"Usuario", "Titulo", "Genero", "Autor"};
        String[] args = new String[] {userlog.toString()};

        Cursor cursor = db.query("libros", campos, "Usuario=?", args, null, null, null);

        StructListBooks[] data = new StructListBooks[cursor.getCount()];
        int i = 0;

        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                data[i] = new StructListBooks("Titulo: " + cursor.getString(cursor.getColumnIndex("Titulo")),
                                                "Genero: " + cursor.getString(cursor.getColumnIndex("Genero")),
                                                "Autor: " + cursor.getString(cursor.getColumnIndex("Autor")));
                i++;
            } while (cursor.moveToNext());
            i=0;
            AdaptadorLibros adaptador =
                    new AdaptadorLibros(this, data);

            listBooks.setAdapter(adaptador);
        }
    }



}
