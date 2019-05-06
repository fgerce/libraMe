package com.fede.librame.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.fede.librame.R;
import com.fede.librame.Adapters.SpinnerAdapterGeneros;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;
import com.fede.librame.Clases.fetchBooks;


public class newbook extends AppCompatActivity {

    private EditText editISBN13, editISBN10, editTitulo, editAutor, editDescripcion, editEdicion, editEncuadernacion, editEditorial, editFecha, editPrecio, editPaginas, editRuta;
    private Spinner spinnerGenero;
    private Button btnCancel, btnNew;
    private SQLiteDatabase db;
    private String userlog;
    private fetchBooks busqueda;
    private ProgressBar progressBar;
    private ConstraintLayout layout;
    static final int CANCELADO = 1;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbook);

        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe",null,1);
        db = usdbh.getWritableDatabase();
        if(db == null)
        {
            finish();
        }

        editISBN13 = findViewById(R.id.editISBN13);
        editISBN10 = findViewById(R.id.editISBN10);
        editTitulo = findViewById(R.id.editTitulo);
        editAutor = findViewById(R.id.editAutor);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        editDescripcion = findViewById(R.id.editDescripcion);
        editEdicion = findViewById(R.id.editEdicion);
        editEncuadernacion = findViewById(R.id.editEncuadernacion);
        editEditorial = findViewById(R.id.editEditorial);
        editFecha = findViewById(R.id.editFecha);
        editPrecio = findViewById(R.id.editPrecio);
        editRuta = findViewById(R.id.editRuta);
        editPaginas = findViewById(R.id.editPaginas);
        btnCancel = findViewById(R.id.btnCancel);
        btnNew = findViewById(R.id.btnEdit);
        spinnerGenero.setAdapter(new SpinnerAdapterGeneros(this, R.layout.spinner, getResources().getStringArray(R.array.listGeneros)));
        progressBar = findViewById(R.id.progressBar);
        layout = findViewById(R.id.layoutNewBook);

        refreshColor(layout);

        userlog = getIntent().getExtras().getString("User","Public");
        if(getIntent().hasExtra("Query"))
        {
                 String query = getIntent().getExtras().getString("Query", "");
                 busqueda = new fetchBooks(query, newbook.this);
                 progressBar.setVisibility(View.VISIBLE);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent();
                setResult(CANCELADO, backIntent);
                finish();
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String ISBN13 = editISBN13.getText().toString();
                    String ISBN10 = editISBN10.getText().toString();
                    String Titulo = editTitulo.getText().toString();
                    String Autor = editAutor.getText().toString();
                    String Genero = spinnerGenero.getSelectedItem().toString();
                    String Desc = editDescripcion.getText().toString();
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

                    //Aca falta revisar que los campos esten correctos...

                    if(AddBookDB(ISBN13, ISBN10, Titulo, Autor, Genero, Desc, Edicion, Encuadernacion, Editorial, Fecha, Precio, Ruta, Paginas) == RESULT_OK) {
                        Intent returnIntent = new Intent();
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }
                    else
                    {
                        //Hubo problemas con la carga del libro...
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(newbook.this, "Error en datos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private int AddBookDB(String ISBN13, String ISBN10, String Titulo, String Autor, String Genero, String Descripcion, int Edicion, String Encuadernacion, String Editorial, String Fecha, float Precio, String Ruta, Integer Paginas)
    {
        String[] campos = new String[] {"Titulo", "Autor", "Edicion", "Usuario"};
        String[] args = new String[] {Titulo, Autor, String.valueOf(Edicion), userlog};

        Cursor c = db.query("libros", campos, "Titulo=? AND Autor=? AND Edicion=? AND Usuario=?", args, null, null, null);

        if(c.getCount() > 0)
        {
            Toast.makeText(this, "Libro ya existente", Toast.LENGTH_SHORT).show();
            c.close();
            return c.getCount();
        }
        else
        {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("ISBN13", ISBN13);
            nuevoRegistro.put("ISBN10", ISBN10);
            nuevoRegistro.put("Titulo", Titulo);
            nuevoRegistro.put("Autor", Autor);
            if(Genero.equals(getResources().getStringArray(R.array.listGeneros)[0]))
            {
             Genero = getString(R.string.otrogenero);
            }
            nuevoRegistro.put("Genero", Genero);
            nuevoRegistro.put("Descripcion", Descripcion);
            nuevoRegistro.put("Edicion", Edicion);
            nuevoRegistro.put("Encuadernacion", Encuadernacion);
            nuevoRegistro.put("Editorial", Editorial);
            nuevoRegistro.put("Fecha", Fecha);
            nuevoRegistro.put("Precio", Precio);
            nuevoRegistro.put("Rutaportada", Ruta);
            nuevoRegistro.put("Usuario", userlog.toString());
            nuevoRegistro.put("Paginas", Paginas);
            nuevoRegistro.put("Estado", "");
            nuevoRegistro.put("Cantidad", String.valueOf(1));
            db.insert("libros", null, nuevoRegistro);
            Toast.makeText(this, "Libro agregado correctamente", Toast.LENGTH_SHORT).show();
            c.close();
            return RESULT_OK;
        }
    }

    public void Refresh(){
         if(busqueda.getStatus() == true){
             editISBN13.setText(busqueda.getISBN13());
             editISBN10.setText(busqueda.getISBN10());
             editTitulo.setText(busqueda.getTitulo());
             editPaginas.setText(String.valueOf(busqueda.getPaginas()));
             editAutor.setText(busqueda.getAutor());
             editEditorial.setText(busqueda.getEditorial());
             editFecha.setText(busqueda.getFechapublicacion());
             editDescripcion.setText(busqueda.getDescripcion());
             editRuta.setText(busqueda.getCoverUrl());
             editEncuadernacion.setText(busqueda.getEncuadernacion());
             if(!busqueda.getCoverUrl().equals("")){
                 editRuta.setEnabled(false);
             }
         }
         else
         {
             Toast.makeText(this, "Busqueda incompleta", Toast.LENGTH_SHORT).show();
         }
         progressBar.setVisibility(View.INVISIBLE);
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
