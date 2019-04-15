package com.fede.librame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public ListView listBooks;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitle(getResources().getString(R.string.menuseleccion));
        toolbar.inflateMenu(R.menu.toolbar_menu);

        final StructListBooks[] datos =
                new StructListBooks[]{
                        new StructListBooks("Título 1", "Subtítulo largo 1"),
                        new StructListBooks("Título 2", "Subtítulo largo 2"),
                        new StructListBooks("Título 3", "Subtítulo largo 3"),
                        new StructListBooks("Título 4", "Subtítulo largo 4"),
                        //...
                        new StructListBooks("Título 15", "Subtítulo largo 15")};

        AdaptadorLibros adaptador =
                new AdaptadorLibros(this, datos);

        listBooks = (ListView)findViewById(R.id.listBooks);

        listBooks.setAdapter(adaptador);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_main_setting:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

}
