package com.temperance2015.reader.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.temperance2015.reader.R;
import com.temperance2015.reader.model.Books;
import com.temperance2015.reader.util.BooklistAdapter;
import com.temperance2015.reader.util.Tools;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView bookListView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Integer> myId = new ArrayList<>();
    private ArrayList<String> myDataSet = new ArrayList<>();
    private ArrayList<String> myReadDate = new ArrayList<>();
    private BooklistAdapter booklistAdapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private void dataToList(){
        List<Books> booksList = DataSupport.findAll(Books.class);
        for (int i = 0;i < booksList.size();i++){
            myId.add(booksList.get(i).getId());
            myDataSet.add(i, booksList.get(i).getTitle());
            myReadDate.add(i, booksList.get(i).getReadDate());
        }
        booklistAdapter = new BooklistAdapter(MainActivity.this,myId,myDataSet,myReadDate);
        bookListView.setAdapter(booklistAdapter);
        booklistAdapter.setOnItemClickListener(new BooklistAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int data) {
                Toast.makeText(MainActivity.this, "You choose book " + data, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("BOOK_ID",data);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = Connector.getDatabase();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        //navigationView
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);

        //recyclerView
        bookListView = (RecyclerView) findViewById(R.id.book_list);
        bookListView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        bookListView.setLayoutManager(linearLayoutManager);
        bookListView.setItemAnimator(new DefaultItemAnimator());
        dataToList();

        //floatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_search:
                                DataSupport.deleteAll(Books.class);
                                myId.clear();
                                myDataSet.clear();
                                myReadDate.clear();
                                Tools.search(Tools.getSDPath());
                                dataToList();
                                Toast.makeText(MainActivity.this,"search in the phone",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.navigation_clear:
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                    dialog.setTitle("清除所有数据！");
                                    dialog.setMessage("清除掉所有书籍和阅读记录，确定吗？");
                                    dialog.setCancelable(false);
                                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DataSupport.deleteAll(Books.class);
                                            myId.clear();
                                            myDataSet.clear();
                                            myReadDate.clear();
                                            booklistAdapter = new BooklistAdapter(MainActivity.this, myId, myDataSet, myReadDate);
                                            bookListView.setAdapter(booklistAdapter);
                                        }
                                    });
                                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    dialog.show();
                                break;
                        }
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
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
