package com.fede.librame.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fede.librame.Adapters.AdaptadorLibros;
import com.fede.librame.R;
import com.fede.librame.Clases.StructListBooks;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView listBooks;
    public Toolbar toolbar;
    public String userlog;
    public SQLiteDatabase db;
    public List<StructListBooks> data = new ArrayList<StructListBooks>();
    public AdaptadorLibros adaptador;
    public ConstraintLayout layout;

    static final int PICK_NEW_BOOK = 1;
    static final int SEARCH_BOOK = 2;

    Dialog customDialog = null;

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
        refreshColor(layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SpeedDialView speedDialView = findViewById(R.id.speedDial);

        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_add, R.drawable.ic_pen)
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.secondaryLightColor))
                .create());
        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_search, R.drawable.ic_filesearch)
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.secondaryLightColor))
                .create());
        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_scan, R.drawable.ic_barcode)
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.secondaryLightColor))
                .create());


        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe",null,1);
        db = usdbh.getWritableDatabase();
        if(db == null)
        {
            finish();
        }

        toolbar = findViewById(R.id.toolbar);
        userlog = getIntent().getExtras().getString("User"," ");
        toolbar.setTitle("Hola " + userlog);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primaryTextColor));
        toolbar.setSubtitle(getResources().getString(R.string.menuseleccion));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primaryTextColor));
        toolbar.inflateMenu(R.menu.toolbar_menu);
        listBooks = findViewById(R.id.listBooks);
        registerForContextMenu(listBooks);
        layout = findViewById(R.id.mainLayout);
        refreshColor(layout);

        try{
            refreshList();
        }catch (Exception e)
        {
            Toast.makeText(this, "Error cargando datos", Toast.LENGTH_SHORT).show();
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.logout:
                        onBackPressed();
                        return true;
                    case R.id.setting:
                        Intent toSettings = new Intent(MainActivity.this, Settings.class);
                        startActivity(toSettings);
                        return true;
                    case R.id.sync:
                        try{
                            refreshList();
                        }catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, "Error cargando datos", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        Toast.makeText(MainActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_add:
                        Intent toNewBook = new Intent().setClass(MainActivity.this, newbook.class);
                        toNewBook.putExtra("User", userlog.toString());
                        startActivityForResult(toNewBook,PICK_NEW_BOOK);
                        return false; // true to keep the Speed Dial open
                    case R.id.fab_scan:
                        Toast.makeText(MainActivity.this, "Implementar scan", Toast.LENGTH_SHORT).show();
                        speedDialView.close(true);
                        return true;
                    case R.id.fab_search:
                        showDialog(findViewById(android.R.id.content));
                        speedDialView.close(true);
                        return true;
                    default:
                        return false;
                }
            }
        });

        listBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StructListBooks item = data.get(position);
                Intent toBookDetails = new Intent().setClass(MainActivity.this, BookDetails.class);
                toBookDetails.putExtra("ID", item.getID());
                toBookDetails.putExtra("User", userlog.toString());
                startActivity(toBookDetails);
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
                    refreshList();
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
        if (requestCode == SEARCH_BOOK){
            if (resultCode == RESULT_OK) {
                try
                {
                    refreshList();
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Error cargando datos", Toast.LENGTH_SHORT).show();
                }
            }
            else if(resultCode == newbook.CANCELADO){

            }
            else{
                Toast.makeText(this, "Solicitar tecnico, problema en newbook", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        StructListBooks libroseleccionado = data.get(info.position);

        switch (item.getItemId()){
            case R.id.edit_item:
                Intent toEditBook = new Intent().setClass(MainActivity.this, editBook.class);
                toEditBook.putExtra("ID", libroseleccionado.getID());
                toEditBook.putExtra("User", userlog.toString());
                startActivity(toEditBook);
                return true;

            case R.id.remove_item:
                db.delete("libros", "id="+ libroseleccionado.getID().toString(), null);
                data.remove(info.position);
                adaptador = new AdaptadorLibros(this, data);
                listBooks.setAdapter(adaptador);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }

    public void refreshList(){
        String[] campos = new String[] {"ID", "Usuario", "Titulo", "Genero", "Autor", "Fecha", "Paginas", "Rutaportada"};
        String[] args = new String[] {userlog.toString()};

        Cursor cursor = db.query("libros", campos, "Usuario=?", args, null, null, null);

        data.clear();
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
            data.add(new StructListBooks(cursor.getInt(cursor.getColumnIndex("ID")),
                                         cursor.getString(cursor.getColumnIndex("Titulo")),
                                        "Genero: " + cursor.getString(cursor.getColumnIndex("Genero")),
                                        "Autor: " + cursor.getString(cursor.getColumnIndex("Autor")),
                                        "Fecha: " + cursor.getString(cursor.getColumnIndex("Fecha")),
                                        cursor.getInt(cursor.getColumnIndex("Paginas")),
                                        Uri.parse(cursor.getString(cursor.getColumnIndex("Rutaportada")))
                                        )
                );
            } while (cursor.moveToNext());
            adaptador = new AdaptadorLibros(this, data);
            listBooks.setAdapter(adaptador);
        }
    }

    public void showDialog(View view){
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialogsearchbook);

        final EditText txtBusqueda = customDialog.findViewById(R.id.txtSearchItem);

        customDialog.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String resultado;
                resultado = txtBusqueda.getText().toString();
                Intent toNewBook = new Intent().setClass(MainActivity.this, newbook.class);
                toNewBook.putExtra("User", userlog.toString());
                toNewBook.putExtra("Query", resultado);
                startActivityForResult(toNewBook,SEARCH_BOOK);
                customDialog.dismiss();
            }
        });

        (customDialog.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Está seguro que desea cerrar sesión?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent toLogin = new Intent().setClass(MainActivity.this, Login.class);
                        startActivity(toLogin);
                        finish();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
