package com.fede.librame.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fede.librame.R;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;

public class Login extends AppCompatActivity {

    public String username;
    public String password;

    private Button btnLogin;
    private Button btnCreate;

    private EditText edituser;
    private EditText editpassword;
    private CheckBox checkStayLog;

    static final int PICK_NEW_USER = 1;

    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe", null, 1);
            db = usdbh.getWritableDatabase();
            if (db == null) {
                finish();
            }
        }catch (Exception e)
        {
            Toast.makeText(this, "Error en carga de base de datos - Consulte servicio", Toast.LENGTH_SHORT).show();
        }

        btnLogin = findViewById(R.id.btn_login);
        btnCreate = findViewById(R.id.btn_create);
        edituser = findViewById(R.id.editUser);
        editpassword = findViewById(R.id.editPassword);
        checkStayLog = findViewById(R.id.checkStayLog);

        try{
            SharedPreferences prefs =
                    getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
            String tmpuser = prefs.getString("User", "");
            String tmppass = prefs.getString("Pass", "");
            Boolean stateCheck = prefs.getBoolean("stateCheck", false);
            edituser.setText(tmpuser.toString());
            editpassword.setText(tmppass.toString());
            checkStayLog.setChecked(stateCheck);
        }catch (Exception e){

        }


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreate = new Intent().setClass(Login.this, CreateUser.class);
                startActivityForResult(toCreate,PICK_NEW_USER);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String struser = edituser.getText().toString();
                String strpass = editpassword.getText().toString();
                
                String[] campos = new String[] {"Usuario", "Contraseña"};
                String[] args = new String[] {struser, strpass};
                Cursor c = db.query("Usuarios", campos, "Usuario=? AND Contraseña=?", args, null, null, null);

                if(c.getCount() == 1)
                {
                    if(checkStayLog.isChecked()) {
                        SharedPreferences prefs =
                                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("User", struser);
                        editor.putString("Pass", strpass);
                        editor.putBoolean("stateCheck", true);
                        editor.commit();
                    }
                    else
                    {
                        SharedPreferences prefs =
                                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("User", "");
                        editor.putString("Pass", "");
                        editor.putBoolean("stateCheck", false);
                        editor.commit();
                    }
                    Toast.makeText(Login.this,R.string.ingresando,Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent().setClass(Login.this, MainActivity.class);
                    toMain.putExtra("User", struser);
                    startActivity(toMain);
                }
                else if(c.getCount() == 0)
                {
                    Toast.makeText(Login.this, R.string.wronguser, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Login.this, "Error en base de datos. Consultar servicio tecnico.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_NEW_USER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String result=data.getStringExtra("result");
                edituser.setText(result);
                editpassword.setText("");
                editpassword.requestFocus();
            }

            if (resultCode == CreateUser.NO_USER) {
                //Toast.makeText(this, "No generado", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
