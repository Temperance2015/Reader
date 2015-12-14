package com.temperance2015.reader.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.temperance2015.reader.R;
import com.temperance2015.reader.util.BooklistAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private RecyclerView bookListView;
    private LinearLayoutManager linearLayoutManager;
    private String[] myDataSet;
    private String[] myReadDate;
    private BooklistAdapter booklistAdapter;

    private void initData(){
        myDataSet = new String[]{"001","002","003","004","005","006","007"};
        DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        myReadDate = new String[]{sdf.format(Calendar.getInstance().getTime()),sdf.format(Calendar.getInstance().getTime()),sdf.format(Calendar.getInstance().getTime()),sdf.format(Calendar.getInstance().getTime()),sdf.format(Calendar.getInstance().getTime()),sdf.format(Calendar.getInstance().getTime()),sdf.format(Calendar.getInstance().getTime())};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bookListView = (RecyclerView) findViewById(R.id.book_list);
        bookListView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        bookListView.setLayoutManager(linearLayoutManager);
        bookListView.setItemAnimator(new DefaultItemAnimator());
        initData();
        booklistAdapter = new BooklistAdapter(this,myDataSet,myReadDate);
        bookListView.setAdapter(booklistAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
