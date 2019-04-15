package com.fede.librame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    public static final String validUser = "Fede";
    public static final String validPass = "1234";
    public String username;
    public String password;

    private Button btnLogin;
    private Button btnCreate;

    private EditText edituser;
    private EditText editpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        btnCreate = findViewById(R.id.btn_create);
        edituser = findViewById(R.id.editUser);
        editpassword = findViewById(R.id.editPassword);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreate = new Intent().setClass(Login.this, CreateUser.class);
                startActivity(toCreate);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edituser.getText().toString();
                password = editpassword.getText().toString();

                if(username.equals(validUser) && password.equals(validPass))
                {
                    Toast.makeText(Login.this,R.string.ingresando,Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent().setClass(Login.this, MainActivity.class);
                    startActivity(toMain);
                }
                else
                {
                    Toast.makeText(Login.this, R.string.wronguser, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
