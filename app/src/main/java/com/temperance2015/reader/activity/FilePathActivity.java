package com.temperance2015.reader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.temperance2015.reader.R;
import com.temperance2015.reader.model.Books;
import com.temperance2015.reader.util.FilePathAdapter;
import com.temperance2015.reader.util.Tools;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isabel on 2016/1/18.
 */
public class FilePathActivity extends AppCompatActivity{

    private RecyclerView filePathView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> pathName = new ArrayList<>();
    private ArrayList<String> pathContent = new ArrayList<>();
    private FilePathAdapter filePathAdapter;
    private FilePathAdapter.OnRecyclerViewItemClickListener clickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clickListener = new FilePathAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String path) {
                File file = new File(path);
                if (file.isDirectory()){
                    pathName = getPathName(file);
                    pathContent = getPathContent(pathName);
                    filePathAdapter = new FilePathAdapter(FilePathActivity.this,pathName,pathContent);
                    filePathView.setAdapter(filePathAdapter);
                    filePathAdapter.setOnItemCLickListener(clickListener);
                }else if (file.isFile()){
                    if (file.getName().endsWith(".txt")){
                        List<Books> bookList = DataSupport.findAll(Books.class);
                        ArrayList<String> pathList = new ArrayList<>();
                        for (Books x : bookList){
                            pathList.add(x.getPath());
                        }
                        if (pathList.contains(file.getPath())){
                            Toast.makeText(FilePathActivity.this,"already in the list",Toast.LENGTH_SHORT).show();
                        }else {
                            Books book = new Books();
                            book.setPath(file.getPath());
                            book.setReadDate(Tools.getDate());
                            book.setTitle(file.getName());
                            book.save();
                            Toast.makeText(FilePathActivity.this,"add a new book",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(FilePathActivity.this,"this is not a book",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        pathName.add(Tools.getSDPath().toString());
        pathContent = getPathContent(pathName);

        filePathView = (RecyclerView) findViewById(R.id.filepath_list);
        filePathView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        filePathView.setLayoutManager(linearLayoutManager);
        filePathView.setItemAnimator(new DefaultItemAnimator());
        filePathAdapter = new FilePathAdapter(FilePathActivity.this,pathName,pathContent);
        filePathView.setAdapter(filePathAdapter);
        filePathAdapter.setOnItemCLickListener(clickListener);

    }

    private ArrayList<String> getPathName(File file){
        ArrayList<String> result = new ArrayList<>();
        try{
            File[] files = file.listFiles();
            if (files.length > 0){
                for (File x : files){
                    result.add(x.getPath());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    private ArrayList<String> getPathContent(ArrayList<String> path){
        ArrayList<String> result = new ArrayList<>();
        for (String x : path){
            int i = x.lastIndexOf("/");
            if (i != -1)
                result.add(x.substring(i+1));
            else
                result.add(x);
        }
        return result;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(FilePathActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
