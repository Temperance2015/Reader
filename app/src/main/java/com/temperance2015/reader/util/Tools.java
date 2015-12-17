package com.temperance2015.reader.util;

import android.graphics.Path;
import android.os.Environment;

import com.temperance2015.reader.model.Books;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Isabel on 2015/12/17.
 */
public class Tools {
    public static String getDate(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static File getPath(){
        File SDPath;
        boolean SDExists = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (SDExists){
            SDPath = Environment.getExternalStorageDirectory();
        }else {
            SDPath = Environment.getRootDirectory();
        }
        return SDPath;
    }

    public static void search(File file){
        try {
            File[] files = file.listFiles();
            if (files.length > 0){
                for (int i = 0; i < files.length; i++){
                    if (!files[i].isDirectory()){
                        if (files[i].getName().contains(".do")){
                            Books book = new Books();
                            book.setPath(files[i].getPath());
                            book.setReadDate(Tools.getDate());
                            book.setTitle(files[i].getName());
                            book.save();
                        }
                    }
                    else {
                        search(files[i]);
                    }
                }
            }
        }catch (Exception e){
        }
    }
}
