package com.fede.librame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;


public class newbook extends AppCompatActivity {

    public EditText editISBN13, editISBN10, editTitulo, editAutor, editDescripcion, editEdicion, editEncuadernacion, editEditorial, editFecha, editPrecio, editPaginas;
    public Spinner spinnerGenero;
    public Button btnCancel, btnNew;
    public SQLiteDatabase db;
    public String userlog;
    public fetchBooks busqueda;
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
        editPaginas = findViewById(R.id.editPaginas);
        btnCancel = findViewById(R.id.btnCancel);
        btnNew = findViewById(R.id.btnAdd);
        spinnerGenero.setAdapter(new SpinnerAdapterGeneros(this, R.layout.spinner, getResources().getStringArray(R.array.listGeneros)));

        userlog = getIntent().getExtras().getString("User","Public");
        if(getIntent().hasExtra("Query"))
        {
                 String query = getIntent().getExtras().getString("Query", "");
                 busqueda = new fetchBooks(query, newbook.this);
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
                    int ISBN13 = Integer.valueOf(editISBN13.getText().toString());
                    int ISBN10 = Integer.valueOf(editISBN10.getText().toString());
                    String Titulo = editTitulo.getText().toString();
                    String Autor = editAutor.getText().toString();
                    String Genero = spinnerGenero.getSelectedItem().toString();
                    String Desc = editDescripcion.getText().toString();
                    int Edicion = Integer.valueOf(editEdicion.getText().toString());
                    String Encuadernacion = editEncuadernacion.getText().toString();
                    String Editorial = editEditorial.getText().toString();
                    String Fecha = editFecha.getText().toString();
                    float Precio = Float.valueOf(editPrecio.getText().toString());
                    int Paginas = Integer.valueOf(editPaginas.getText().toString());

                    //Aca falta revisar que los campos esten correctos...

                    if(AddBookDB(ISBN13, ISBN10, Titulo, Autor, Genero, Desc, Edicion, Encuadernacion, Editorial, Fecha, Precio, Paginas) == RESULT_OK) {
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

    private int AddBookDB(int ISBN13, int ISBN10, String Titulo, String Autor, String Genero, String Descripcion, int Edicion, String Encuadernacion, String Editorial, String Fecha, float Precio, Integer Paginas)
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
            nuevoRegistro.put("Genero", Genero);
            nuevoRegistro.put("Descripcion", Descripcion);
            nuevoRegistro.put("Edicion", Edicion);
            nuevoRegistro.put("Encuadernacion", Encuadernacion);
            nuevoRegistro.put("Editorial", Editorial);
            nuevoRegistro.put("Fecha", Fecha);
            nuevoRegistro.put("Precio", Precio);
            nuevoRegistro.put("Rutaportada", "");
            nuevoRegistro.put("Usuario", userlog.toString());
            nuevoRegistro.put("Paginas", Paginas);
            db.insert("libros", null, nuevoRegistro);
            Toast.makeText(this, "Libro agregado correctamente", Toast.LENGTH_SHORT).show();
            c.close();
            return RESULT_OK;
        }
    }

    public void Refresh(){
         if(busqueda.getStatus() == true){
             Toast.makeText(this, busqueda.getISBN10(), Toast.LENGTH_SHORT).show();
             editISBN13.setText(busqueda.getISBN13());
             editISBN10.setText(busqueda.getISBN10());
             editTitulo.setText(busqueda.getTitulo());
             editPaginas.setText(busqueda.getPaginas());
             editAutor.setText(busqueda.getAutor());
             editEditorial.setText(busqueda.getEditorial());
             editFecha.setText(busqueda.getFechapublicacion());
         }
         else
         {
             Toast.makeText(this, "Busqueda incompleta", Toast.LENGTH_SHORT).show();
         }
    }
}
