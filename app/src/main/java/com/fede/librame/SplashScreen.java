package com.fede.librame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            copyDataBase();
        }catch (Exception e)
        {
            Toast.makeText(this, "Error base de datos", Toast.LENGTH_SHORT).show();
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
        InputStream myInput = SplashScreen.this.getAssets().open("libraMe.db");

        // Path to the just created empty db
        String outFileName = "/data/data/com.fede.librame/databases/libraMe.db";
        // String outFileName = context.getDatabasePath("Manatab").getPath();
        //Open the empty db as the output stream
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

