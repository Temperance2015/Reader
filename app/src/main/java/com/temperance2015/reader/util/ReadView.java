package com.temperance2015.reader.util;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Isabel on 2016/1/17.
 */
public class ReadView extends TextView{

    public ReadView(Context context,AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
    }

    public ReadView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public ReadView(Context context){
        super(context);
    }

    @Override
    protected void onLayout(boolean changed,int left,int top,int right,int bottom){
        super.onLayout(changed,left,top,right,bottom);
        resize();
    }

    /**
     * 去除当前页无法显示的字
     * @return 去掉的字数
     */
    public int resize(){
        CharSequence oldContent = getText();
        CharSequence newContent = oldContent.subSequence(0,getCharNum());
        setText(newContent);
        return oldContent.length() - newContent.length();
    }

    /**
     * 获取当前页总字数
     */
    public int getCharNum(){
        return getLayout().getLineEnd(getLineNum());
    }

    /**
     * 获取当前页总行数
     */
    public int getLineNum(){
        Layout layout = getLayout();
        int topOfLastLine = getHeight() - getPaddingTop() - getPaddingBottom() - getLineHeight();
        return layout.getLineForVertical(topOfLastLine);
    }


}
