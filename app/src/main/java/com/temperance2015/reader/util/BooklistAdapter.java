package com.temperance2015.reader.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.temperance2015.reader.R;

import java.util.ArrayList;


/**
 * Created by Isabel on 2015/12/14.
 */
public class BooklistAdapter extends RecyclerView.Adapter<BooklistAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Integer> bookId;
    private ArrayList<String> bookTitle;
    private ArrayList<String> readDate;
    private final int background;
    private final TypedValue typedValue = new TypedValue();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
     public void onClick(View view){
        if (mOnItemClickListener != null) {
            //使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public BooklistAdapter(Context context,ArrayList<Integer> id,ArrayList<String> title,ArrayList<String> date){
        bookId = id;
        bookTitle = title;
        readDate = date;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground,typedValue,true);
        background = typedValue.resourceId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView title;
        public TextView date;

        public ViewHolder(View v){
            super(v);
            id = (TextView) v.findViewById(R.id.book_Id);
            title = (TextView) v.findViewById(R.id.book_title);
            date = (TextView) v.findViewById(R.id.read_date);
        }
    }

    @Override
    public BooklistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item,parent,false);
        v.setBackgroundResource(background);
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(this);//将创建的View注册点击事件
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        holder.id.setText(bookId.get(position).toString());
        holder.title.setText(bookTitle.get(position));
        holder.date.setText(readDate.get(position));
        holder.itemView.setTag(bookId.get(position));//将数据保存在itemView的Tag中，点击时进行获取
    }

    @Override
    public int getItemCount(){
        return bookTitle.size();
    }
}