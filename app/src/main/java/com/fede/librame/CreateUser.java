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
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser extends AppCompatActivity {

    public Button btnBack;
    public Button btnCreate;
    public EditText editPassword;
    public EditText editMail;
    public EditText editUser;
    static final int NO_USER = 2;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe",null,1);
        db = usdbh.getWritableDatabase();
        if(db == null)
        {
            finish();
        }

        btnBack = findViewById(R.id.btn_back);
        btnCreate = findViewById(R.id.btn_create);
        editPassword = findViewById(R.id.editPassword);
        editMail = findViewById(R.id.editEmail);
        editUser = findViewById(R.id.editUser);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(NO_USER, returnIntent);
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editUser.getText().toString().equals(""))
                {
                    Toast.makeText(CreateUser.this, "Usuario invalido", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(editPassword.getText().toString().equals(""))
                {
                    Toast.makeText(CreateUser.this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!isEmailValid(editMail.getText().toString()))
                {
                    Toast.makeText(CreateUser.this, "Email invalido", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(AddUserDB(editUser.getText().toString(), editPassword.getText().toString(),editMail.getText().toString()) == 0)
                    {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",editUser.getText().toString());
                        setResult(Login.RESULT_OK, returnIntent);
                        finish();
                    }
                    else
                    {
                        editUser.setText("");
                        editMail.setText("");
                        editPassword.setText("");
                    }

                }

            }
        });
    }

    private int AddUserDB(String struser, String strpass, String stremail)
    {
        String[] campos = new String[] {"Usuario", "Email"};
        String[] args = new String[] {struser, stremail};
        Cursor c = db.query("Usuarios", campos, "Usuario=? OR Email=?", args, null, null, null);

        if(c.getCount() > 0)
        {
            Toast.makeText(this, "Usuario/Email ya existente", Toast.LENGTH_SHORT).show();
            int aux = c.getCount();
            c.close();
            return aux;
        }
        else
        {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("Usuario", struser);
            nuevoRegistro.put("Email", stremail);
            nuevoRegistro.put("Contraseña", strpass);
            db.insert("usuarios", null, nuevoRegistro);
            Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
            c.close();
            return 0;
        }
    }
    public static boolean isEmailValid(String email)
    {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
