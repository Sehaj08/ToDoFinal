package com.example.sehajgulati08.todofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sehaj.gulati08 on 07-07-2017.
 */

public class ToDoAdapter extends ArrayAdapter<ToDoItems> {
    ArrayList<ToDoItems> toDoArrayList;
    Context context;
    public ToDoAdapter(Context context, ArrayList<ToDoItems> toDoArrayList) {
        super(context,0);
        this.toDoArrayList = toDoArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return toDoArrayList.size();
    }

    static class ToDoViewHolder{
       TextView titleTextView;

        ToDoViewHolder(TextView titleTextView){
            this.titleTextView = titleTextView;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view,null);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.toDoTextView);
            ToDoViewHolder toDoViewHolder = new ToDoViewHolder(titleTextView);
            convertView.setTag(toDoViewHolder);
        }
        ToDoItems t = toDoArrayList.get(position);
        ToDoViewHolder toDoViewHolder = (ToDoViewHolder)convertView.getTag();
        toDoViewHolder.titleTextView.setText(t.title);

        return  convertView;
    }
}
