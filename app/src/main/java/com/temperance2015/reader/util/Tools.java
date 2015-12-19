package com.temperance2015.reader.util;

import android.os.Environment;
import android.util.Log;

import com.temperance2015.reader.model.Books;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    public static File getSDPath(){
        File SDPath;
        boolean SDExists = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (SDExists){
            SDPath = Environment.getExternalStorageDirectory();
//            Log.d("SDDirectory","SDPath is "+SDPath.toString());
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
                        if (files[i].getName().endsWith(".txt")){
//                            Log.d("search",i+" Path:"+files[i].getPath()+" name:"+files[i].getName());
                            Books book = new Books();
                            book.setPath(files[i].getPath());
                            book.setReadDate(Tools.getDate());
                            book.setTitle(files[i].getName());
                            book.save();
                        }
                    }
//                    else {
//                        search(files[i]);
//                    }
                }
            }
        }catch (Exception e){
        }
    }

    public static String ReadFile(File file){
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
