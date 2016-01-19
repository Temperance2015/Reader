package com.temperance2015.reader.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.temperance2015.reader.R;

import java.util.ArrayList;

/**
 * Created by Isabel on 2016/1/18.
 */
public class FilePathAdapter extends RecyclerView.Adapter<FilePathAdapter.ViewHolder> implements View.OnClickListener{

    private OnRecyclerViewItemClickListener onItemClickListener = null;
    private ArrayList<String> pathName;
    private ArrayList<String> pathContent;
    private final int background;
    private final TypedValue typedValue = new TypedValue();

    public interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,String path);
    }

    public void setOnItemCLickListener(OnRecyclerViewItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public void onClick(View view){
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(view,(String)view.getTag());
        }
    }

    public FilePathAdapter(Context context,ArrayList<String> filePath,ArrayList<String> filePathContent){
        pathName = filePath;
        pathContent = filePathContent;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        background = typedValue.resourceId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView filePathName;
        public TextView filePathContent;

        public ViewHolder(View v){
            super(v);
            filePathName = (TextView) v.findViewById(R.id.path_name);
            filePathContent = (TextView) v.findViewById(R.id.path_content);
        }
    }

    @Override
    public FilePathAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.path_list_item,parent,false);
        v.setBackgroundResource(background);
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(this);//将创建的View注册点击事件
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        holder.filePathName.setText(pathName.get(position));
        holder.filePathContent.setText(pathContent.get(position));
        holder.itemView.setTag(pathName.get(position));//将数据保存在itemView的Tag中，点击时进行获取
    }

    @Override
    public int getItemCount(){
        return pathName.size();
    }
}
