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


/**
 * Created by Isabel on 2015/12/14.
 */
public class BooklistAdapter extends RecyclerView.Adapter<BooklistAdapter.ViewHolder> {

    private String[] bookTitle;
    private String[] readDate;
    private final int background;
    private final TypedValue typedValue = new TypedValue();

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public TextView date;

        public ViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.book_title);
            date = (TextView) v.findViewById(R.id.read_date);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            String text = "you click book " + title.getText() + "read in " + date.getText();
            Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show();
        }

    }

    public BooklistAdapter(Context context,String[] title,String[] date){
        bookTitle = title;
        readDate = date;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground,typedValue,true);
        background = typedValue.resourceId;
    }

    @Override
    public BooklistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item,parent,false);
        v.setBackgroundResource(background);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        holder.title.setText(bookTitle[position]);
        holder.date.setText(readDate[position]);
    }

    @Override
    public int getItemCount(){
        return bookTitle.length;
    }
}
