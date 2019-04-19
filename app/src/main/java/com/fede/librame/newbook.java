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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;


public class newbook extends AppCompatActivity {

    public TextView txtISBN13, txtISBN10,txtTitulo,txtAutor,txtGenero,txtDescripcion,txtEdicion, txtEncuadernacion, txtEditorial, txtFecha, txtPrecio;
    public EditText editISBN13, editISBN10, editTitulo, editAutor, editGenero, editDescripcion, editEdicion, editEncuadernacion, editEditorial, editFecha, editPrecio;
    public Button btnCancel, btnNew;
    public SQLiteDatabase db;

    static final int CANCELADO = 1;
    static final int CARGA_OK = 0;

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


        txtISBN13 = findViewById(R.id.txtISBN13);
        txtISBN10 = findViewById(R.id.txtISBN10);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor  = findViewById(R.id.txtAutor);
        txtGenero = findViewById(R.id.txtGenero);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtEdicion = findViewById(R.id.txtEdicion);
        txtEncuadernacion = findViewById(R.id.txtEncuadernacion);
        txtEditorial = findViewById(R.id.txtEditorial);
        txtFecha = findViewById(R.id.txtFecha);
        txtPrecio = findViewById(R.id.txtPrecio);
        editISBN13 = findViewById(R.id.editISBN13);
        editISBN10 = findViewById(R.id.editISBN10);
        editTitulo = findViewById(R.id.editTitulo);
        editAutor = findViewById(R.id.editAutor);
        editGenero = findViewById(R.id.editGenero);
        editDescripcion = findViewById(R.id.editDescripcion);
        editEdicion = findViewById(R.id.editEdicion);
        editEncuadernacion = findViewById(R.id.editEncuadernacion);
        editEditorial = findViewById(R.id.editEditorial);
        editFecha = findViewById(R.id.editFecha);
        editPrecio = findViewById(R.id.editPrecio);
        btnCancel = findViewById(R.id.btnCancel);
        btnNew = findViewById(R.id.btnAdd);


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
                    String Autor = editTitulo.getText().toString();
                    String Genero = editGenero.getText().toString();
                    String Desc = editDescripcion.getText().toString();
                    int Edicion = Integer.valueOf(editEdicion.getText().toString());
                    String Encuadernacion = editEncuadernacion.getText().toString();
                    String Editorial = editEditorial.getText().toString();
                    String Fecha = editFecha.getText().toString();
                    float Precio = Float.valueOf(editPrecio.getText().toString());
                    int retorno;

                    //Aca hay que revisar que los campos esten correctos...

                    retorno = AddBookDB(ISBN13, ISBN10, Titulo, Autor, Genero, Desc, Edicion, Encuadernacion, Editorial, Fecha, Precio);
                    if(retorno == CARGA_OK) {
                        Intent returnIntent = new Intent();
                        setResult(retorno, returnIntent);
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

    private int AddBookDB(int ISBN13, int ISBN10, String Titulo, String Autor, String Genero, String Descripcion, int Edicion, String Encuadernacion, String Editorial, String Fecha, float Precio)
    {
        String[] campos = new String[] {"Titulo", "Autor", "Edicion"};
        String[] args = new String[] {Titulo, Autor, String.valueOf(Edicion)};

        Cursor c = db.query("libros", campos, "Titulo=? AND Autor=? AND Edicion=?", args, null, null, null);

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
            db.insert("libros", null, nuevoRegistro);
            Toast.makeText(this, "Libro agregado correctamente", Toast.LENGTH_SHORT).show();
            c.close();
            return CARGA_OK;
        }
    }
}
