package com.temperance2015.reader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.temperance2015.reader.R;
import com.temperance2015.reader.model.Books;
import com.temperance2015.reader.util.ReadView;
import com.temperance2015.reader.util.Tools;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Isabel on 2015/12/19.
 */
public class ShowActivity extends AppCompatActivity {
    private ReadView show_book_content;
    private BufferedReader bufferedReader;
    private String textCache;
    private StringBuilder textContent = new StringBuilder();
    private int textLength;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        show_book_content = (ReadView) findViewById(R.id.show_book_content);
        //要打开哪本书
        Intent intent = getIntent();
        int bookId = intent.getIntExtra("BOOK_ID", -1);
        Books bookReading = DataSupport.find(Books.class, bookId);
        final File file = new File(bookReading.getPath());

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Tools.getTxtType(file)));
            for (int i = 0;i < 5;i++){
                if ((textCache = bufferedReader.readLine()) != null){
                    textContent.append(textCache);
                }
                else break;
            }
            bufferedReader.mark(2000);
        }catch(Exception e){
            e.printStackTrace();
        }

        show_book_content.setText(textContent);

        show_book_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textLength = show_book_content.getText().toString().length();
                textContent.delete(0, textLength);
                if (textContent.length() < textLength){
                    try {
                        bufferedReader.reset();
                        for (int i = 0;i < 5;i++){
                            if ((textCache = bufferedReader.readLine()) != null){
                                textContent.append(textCache);
                            }
                            else break;
                        }
                        bufferedReader.mark(2000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                show_book_content.setText(textContent);
                Toast.makeText(ShowActivity.this, "text length = "+ textLength, Toast.LENGTH_SHORT).show();
            }
        });

        show_book_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ShowActivity.this,"long click",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public void onBackPressed(){
        try{
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }

}
