package com.temperance2015.reader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.temperance2015.reader.R;
import com.temperance2015.reader.model.Books;
import com.temperance2015.reader.util.Tools;

import org.litepal.crud.DataSupport;

import java.io.File;

/**
 * Created by Isabel on 2015/12/19.
 */
public class ShowActivity extends AppCompatActivity {
    private TextView show_book_content;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        show_book_content = (TextView) findViewById(R.id.show_book_content);
        Intent intent = getIntent();
        int bookId = intent.getIntExtra("BOOK_ID", -1);
        Books bookReading = DataSupport.find(Books.class, bookId);
        File file = new File(bookReading.getPath());
        show_book_content.setText(Tools.ReadFile(file));
    }
}
