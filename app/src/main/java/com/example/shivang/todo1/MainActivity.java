package com.example.shivang.todo1;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ToDo todo;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<ToDo> todos = database.todoDao().getAllTodos(false);
        final ListFragment frag1 = new ListFragment();
        final EmptyListFragment frag2 = new EmptyListFragment();

        if (todos.size()==0) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,frag2).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().add(R.id.container,frag1).commit();
        }


//        getSupportFragmentManager().beginTransaction().add(R.id.container,frag1).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this,AddToDo.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.icDone) {
            Intent i = new Intent(MainActivity.this,TasksDone.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
