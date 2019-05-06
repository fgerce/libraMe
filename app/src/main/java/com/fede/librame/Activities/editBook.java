package com.fede.librame.Activities;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fede.librame.Adapters.SpinnerAdapterGeneros;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;
import com.fede.librame.R;

public class editBook extends AppCompatActivity {

    private Integer ID;
    private String usuario;
    private SQLiteDatabase db;
    private EditText editISBN13, editISBN10, editTitulo, editAutor, editDescripcion, editEdicion, editEncuadernacion, editEditorial, editFecha, editPrecio, editPaginas, editRuta;
    private Button btnCancel, btnEdit;
    private Spinner spinnerGenero;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        ID = getIntent().getExtras().getInt("ID", -1);
        usuario = getIntent().getExtras().getString("User", "");

        editISBN13 = findViewById(R.id.editISBN13);
        editISBN10 = findViewById(R.id.editISBN10);
        editTitulo = findViewById(R.id.editTitulo);
        editAutor = findViewById(R.id.editAutor);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        spinnerGenero.setAdapter(new SpinnerAdapterGeneros(this, R.layout.spinner, getResources().getStringArray(R.array.listGeneros)));
        editDescripcion = findViewById(R.id.editDescripcion);
        editEdicion = findViewById(R.id.editEdicion);
        editEncuadernacion = findViewById(R.id.editEncuadernacion);
        editEditorial = findViewById(R.id.editEditorial);
        editFecha = findViewById(R.id.editFecha);
        editPrecio = findViewById(R.id.editPrecio);
        editRuta = findViewById(R.id.editRuta);
        editPaginas = findViewById(R.id.editPaginas);
        btnCancel = findViewById(R.id.btnCancel);
        btnEdit = findViewById(R.id.btnEdit);
        layout = findViewById(R.id.editBookLayout);
        refreshColor(layout);

        if(ID != -1) {
            try {
                UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe", null, 1);
                db = usdbh.getReadableDatabase();
                if (db == null) {
                    finish();
                }
                String[] campos = new String[]{"ID", "ISBN13", "ISBN10", "Titulo", "Autor", "Genero", "Descripcion",
                        "Edicion", "Encuadernacion", "Editorial", "Fecha", "Precio",
                        "Paginas", "Rutaportada", "Estado", "Cantidad", "Usuario"};
                String[] args = new String[]{String.valueOf(ID), usuario};
                Cursor cursor = db.query("libros", campos, "ID=? AND Usuario=?", args, null, null, null);

                if (cursor.getCount() == 1) {
                    if (cursor.moveToFirst()) {
                        //Loop through the table rows
                        do {
                            editISBN13.setText(cursor.getString(cursor.getColumnIndex("ISBN13")));
                            editISBN10.setText(cursor.getString(cursor.getColumnIndex("ISBN10")));
                            editTitulo.setText(cursor.getString(cursor.getColumnIndex("Titulo")));
                            editAutor.setText(cursor.getString(cursor.getColumnIndex("Autor")));
                            String[] androidStrings = getResources().getStringArray(R.array.listGeneros);
                            for(int i=0; i < androidStrings.length; i++){
                                if(androidStrings[i].contains(cursor.getString(cursor.getColumnIndex("Genero"))))
                                {
                                    spinnerGenero.setSelection(i);
                                }
                            }
                            editDescripcion.setText(cursor.getString(cursor.getColumnIndex("Descripcion")));
                            editEdicion.setText(String.valueOf(cursor.getColumnIndex("Edicion")));
                            editEncuadernacion.setText(cursor.getString(cursor.getColumnIndex("Encuadernacion")));
                            editEditorial.setText(cursor.getString(cursor.getColumnIndex("Editorial")));
                            editFecha.setText(cursor.getString(cursor.getColumnIndex("Fecha")));
                            editPrecio.setText(String.valueOf(cursor.getColumnIndex("Precio")));
                            editPaginas.setText(String.valueOf(cursor.getColumnIndex("Paginas")));
                            editRuta.setText(cursor.getString(cursor.getColumnIndex("Rutaportada")));
/*


                        paginas = cursor.getInt(cursor.getColumnIndex("Paginas"));
                        rutaportada = Uri.parse(cursor.getString(cursor.getColumnIndex("Rutaportada")));
                        estado = cursor.getString(cursor.getColumnIndex("Estado"));
                        cantidad = cursor.getInt(cursor.getColumnIndex("Cantidad"));
*/
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error en carga de base de datos - Consulte tecnico", Toast.LENGTH_SHORT).show();
            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ISBN13 = editISBN13.getText().toString();
                String ISBN10 = editISBN10.getText().toString();
                String Titulo = editTitulo.getText().toString();
                String Autor = editAutor.getText().toString();
                String Genero = spinnerGenero.getSelectedItem().toString();
                String Descripcion = editDescripcion.getText().toString();
                if(editEdicion.getText().toString().equals(""))
                {
                    editEdicion.setText("1");
                }
                int Edicion = Integer.valueOf(editEdicion.getText().toString());
                String Encuadernacion = editEncuadernacion.getText().toString();
                String Editorial = editEditorial.getText().toString();
                String Fecha = editFecha.getText().toString();
                if(editPrecio.getText().toString().equals(""))
                {
                    editPrecio.setText("0");
                }
                float Precio = Float.valueOf(editPrecio.getText().toString());
                if(editPaginas.getText().toString().equals(""))
                {
                    editPaginas.setText("0");
                }
                int Paginas = Integer.valueOf(editPaginas.getText().toString());
                String Ruta = editRuta.getText().toString();

                //Edito en SQLite
                ContentValues editReg = new ContentValues();
                editReg.put("ISBN13", ISBN13);
                editReg.put("ISBN10", ISBN10);
                editReg.put("Titulo", Titulo);
                editReg.put("Autor", Autor);
                if(Genero.equals(getResources().getStringArray(R.array.listGeneros)[0]))
                {
                    Genero = getString(R.string.otrogenero);
                }
                editReg.put("Genero", Genero);
                editReg.put("Descripcion", Descripcion);
                editReg.put("Edicion", Edicion);
                editReg.put("Encuadernacion", Encuadernacion);
                editReg.put("Editorial", Editorial);
                editReg.put("Fecha", Fecha);
                editReg.put("Precio", Precio);
                editReg.put("Rutaportada", Ruta);
                editReg.put("Usuario", usuario);
                editReg.put("Paginas", Paginas);
                editReg.put("Estado", "");
                editReg.put("Cantidad", String.valueOf(1));

                //Lugar donde modificar *ID obtenido previamente. Se debe armar el pedido SQL
                String args = "ID=" + ID.toString();
                db.update("libros", editReg, args, null);
                finish();
            }
        });
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
