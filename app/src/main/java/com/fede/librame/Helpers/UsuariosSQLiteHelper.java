package com.fede.librame.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    private String sqlCreate = "CREATE TABLE usuarios (ID INTEGER PRIMARY KEY AUTOINCREMENT, Usuario TEXT unique, Email TEXT unique, Contraseña TEXT)";

    private String sqlCreate2 = "CREATE TABLE libros (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ISBN13 TEXT, ISBN10 TEXT, Titulo TEXT," +
                        "Autor TEXT, Genero TEXT, Descripcion TEXT, Edicion INTEGER, Encuadernacion TEXT, Editorial TEXT, Fecha TEXT, " +
                        "Precio REAL, Rutaportada TEXT, Usuario TEXT, Paginas INTEGER, Estado TEXT, Cantidad INTEGER)";


    public UsuariosSQLiteHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS libros");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
    }
}