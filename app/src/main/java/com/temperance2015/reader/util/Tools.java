package com.temperance2015.reader.util;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.temperance2015.reader.model.Books;

import org.litepal.crud.DataSupport;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                List<Books> booksList = DataSupport.findAll(Books.class);
                Set<String> pathList = new HashSet<>();
                for (int j = 0;j < booksList.size();j++){
                    pathList.add(booksList.get(j).getPath());
                }
                for (int i = 0; i < files.length; i++){
                    if (!files[i].isDirectory()){
                        if (files[i].getName().endsWith(".txt")){
//                            Log.d("search",i+" Path:"+files[i].getPath()+" name:"+files[i].getName());
                            if (!pathList.contains(files[i].getPath())){
                                Books book = new Books();
                                book.setPath(files[i].getPath());
                                book.setReadDate(Tools.getDate());
                                book.setTitle(files[i].getName());
                                book.save();
                            }
                        }
                    }
//                    else {
//                        search(files[i]);
//                    }//进入目录下继续搜索,不用
                }
            }
        }catch (Exception e){
        }
    }

    public static String getTxtType(File file) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }
//    java	        txt
//    unicode	 unicode big endian
//    utf-8	     utf-8
//    utf-16	 unicode
//    gb2312	 ANSI

    public static String ReadFile(File file) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), Tools.getTxtType(file));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }
}
