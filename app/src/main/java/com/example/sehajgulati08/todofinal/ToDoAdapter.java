package com.example.sehajgulati08.todofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sehaj.gulati08 on 07-07-2017.
 */

public class ToDoAdapter extends ArrayAdapter<ToDoItems>  {
    ArrayList<ToDoItems> toDoArrayList,toDoArrayListCopy;
    Context context;
    public ToDoAdapter(Context context, ArrayList<ToDoItems> toDoArrayList) {
        super(context,0);
        this.toDoArrayList = toDoArrayList;
        this.context = context;
        toDoArrayListCopy=toDoArrayList;
    }

    @Override
    public int getCount() {
        return toDoArrayList.size();
    }

    public void filter(String newText) {
        Log.i("Adapter comment",newText);
//        newText = newText.toLowerCase();
        toDoArrayList= new ArrayList<>();
        if(newText.equals("All")){
            toDoArrayList=toDoArrayListCopy;
            notifyDataSetChanged();
        }
        else{
            for(int i = 0 ; i < toDoArrayListCopy.size() ; i++) {
                ToDoItems t = toDoArrayListCopy.get(i);
                    if (t.category.equals(newText)) {
                        toDoArrayList.add(t);
                    }

                notifyDataSetChanged();
                }



            }

        }


    static class ToDoViewHolder{
       TextView titleTextView;
       TextView dateTextView;
        TextView timeTextView;

        ToDoViewHolder(TextView titleTextView , TextView dateTextView,TextView timeTextView){
            this.titleTextView = titleTextView;
            this.dateTextView = dateTextView;
            this.timeTextView = timeTextView;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view,null);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.toDoTextView);
            TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            TextView timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
            ToDoViewHolder toDoViewHolder = new ToDoViewHolder(titleTextView,dateTextView,timeTextView);
            convertView.setTag(toDoViewHolder);
        }
        ToDoItems t = toDoArrayList.get(position);
        ToDoViewHolder toDoViewHolder = (ToDoViewHolder)convertView.getTag();
        toDoViewHolder.titleTextView.setText(t.title);
        toDoViewHolder.dateTextView.setText(t.date);
        toDoViewHolder.timeTextView.setText(t.time);

        return  convertView;
    }
}
