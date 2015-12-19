package com.temperance2015.reader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.temperance2015.reader.R;

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
        show_book_content.setText("sdafsdagsdafsdafsadfsadfsdafsdfsdafsdafsagtrjtyjktdjd");
    }
}
