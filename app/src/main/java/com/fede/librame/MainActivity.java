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

public class MainActivity extends AppCompatActivity {

    public ListView listBooks;
    public Toolbar toolbar;
    public String userlog;
    public FloatingActionButton AddButton;
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

        AddButton = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        userlog = getIntent().getExtras().getString("User"," ");
        toolbar.setTitle("Hola " + userlog);
        toolbar.setSubtitle(getResources().getString(R.string.menuseleccion));
        toolbar.inflateMenu(R.menu.toolbar_menu);
        listBooks = (ListView)findViewById(R.id.listBooks);


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
                startActivityForResult(toNewBook,PICK_NEW_BOOK);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_NEW_BOOK) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

            }
            else
            {

            }
        }
        else
        {

        }
    }
/*
    //Hay que hacer esta funcion...
    public void RefreshList()
    {
        String selectQuery = "SELECT * FROM " + "libros";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {

            } while (cursor.moveToNext());
        }
        final StructListBooks[] datos =
                new StructListBooks[]{
                        new StructListBooks("Título 1", "Subtítulo largo 1"),
                        new StructListBooks("Título 2", "Subtítulo largo 2"),
                        new StructListBooks("Título 3", "Subtítulo largo 3"),
                        new StructListBooks("Título 4", "Subtítulo largo 4"),
                        //...
                        new StructListBooks("Título 15", "Subtítulo largo 15")};

        AdaptadorLibros adaptador =
                new AdaptadorLibros(this, datos);

        listBooks.setAdapter(adaptador);
    }
*/
}
