package com.fede.librame;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        btnBack = findViewById(R.id.btn_back);
        btnCreate = findViewById(R.id.btn_create);
        editPassword = findViewById(R.id.editPassword);
        editMail = findViewById(R.id.editEmail);
        editUser = findViewById(R.id.editUser);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent().setClass(CreateUser.this, Login.class);
                startActivity(backIntent);

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editUser.getText().toString().equals(""))
                {
                    Toast.makeText(CreateUser.this, "Usuario invalido", Toast.LENGTH_SHORT).show();
                }
                if(isEmailValid(editMail.getText().toString()))
                {
                    Toast.makeText(CreateUser.this, "Email OK", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CreateUser.this, "Email invalido", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
