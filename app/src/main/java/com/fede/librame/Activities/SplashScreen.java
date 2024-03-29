package com.fede.librame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fede.librame.R;
import com.fede.librame.Helpers.UsuariosSQLiteHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        File dbFile = getApplicationContext().getDatabasePath("libraMe");
        if (!dbFile.exists())
        {
            try {
                copyDataBase();
            }catch (Exception e)
            {
                Toast.makeText(this, "Error en la base de datos", Toast.LENGTH_SHORT).show();
            }
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent loginIntent = new Intent().setClass(SplashScreen.this, Login.class);
                startActivity(loginIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    public void copyDataBase() throws IOException
    {
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "libraMe.db", null, 1);
        //Open your local db as the input stream
        InputStream myInput = getApplicationContext().getAssets().open("libraMe.db");
        // Path to the just created empty db

        String outFileName = getApplicationContext().getDatabasePath("libraMe").getAbsolutePath();

        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}

