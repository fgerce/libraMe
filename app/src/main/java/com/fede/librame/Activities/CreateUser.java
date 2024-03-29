package com.fede.librame.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fede.librame.R;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser extends AppCompatActivity {

    private Button btnBack;
    private Button btnCreate;
    private EditText editPassword;
    private EditText editMail;
    private EditText editUser;
    private TextInputLayout layouteditUser;
    private TextInputLayout layouteditPassword;
    private TextInputLayout layouteditEmail;
    static final int NO_USER = 2;
    private SQLiteDatabase db;
    private ConstraintLayout layout;

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
        layouteditEmail = findViewById(R.id.layouteditEmail);
        layouteditPassword = findViewById(R.id.layouteditPassword);
        layouteditUser = findViewById(R.id.layouteditUser);
        layout = findViewById(R.id.layoutCreateUser);
        refreshColor(layout);

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

                boolean error = false;

                if(editUser.getText().toString().equals(""))
                {
                    layouteditUser.setError("Usuario invalido");
                    error = true;
                }
                else
                {
                    layouteditUser.setError(null);
                }
                if(editPassword.getText().toString().equals(""))
                {
                    layouteditPassword.setError("Contraseña invalida");
                    error = true;
                }
                else
                {
                    layouteditPassword.setError(null);
                }
                if(!isEmailValid(editMail.getText().toString()))
                {
                    layouteditEmail.setError("Email invalido");
                    error = true;
                }
                else
                {
                    layouteditEmail.setError(null);
                }

                if(error == true)
                {
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
