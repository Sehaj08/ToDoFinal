package com.example.sehajgulati08.todofinal;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    ArrayList<ToDoItems> toDoArrayList;
    ToDoAdapter toDoAdapter;
    int TODO_CODE = 1;
    EditText quickEditTextView;
    Button buttonQuickAdd;
    Spinner spinnerSort;
    ArrayList<String> categoryArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quickEditTextView = (EditText)findViewById(R.id.quickEditTextView);
        buttonQuickAdd = (Button)findViewById(R.id.buttonQuickAdd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.toDoListView);
        toDoArrayList = new ArrayList<>();
        toDoAdapter = new ToDoAdapter(this,toDoArrayList);
        listView.setAdapter(toDoAdapter);
        spinnerSort= (Spinner) findViewById(R.id.spinnerMain);
        setSupportActionBar(toolbar);
        categoryArrayList.add("Home");
        categoryArrayList.add("Personal");
        categoryArrayList.add("Shopping");
        categoryArrayList.add("Work");
        categoryArrayList.add("Others");
        categoryArrayList.add("All");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this , ToDoDetailActivity.class);
                startActivityForResult(i,TODO_CODE);
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, "You can add task by clicking this", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this , ToDoDetailActivity.class);
                i.putExtra(IntentConstants.ID,toDoArrayList.get(position).id);
                startActivityForResult(i,TODO_CODE);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("REMOVE");
                builder.setMessage("Do you want to remove this task?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getToDoOpenHelperInstance(MainActivity.this);
                        SQLiteDatabase database = toDoOpenHelper.getWritableDatabase();
                        database.delete(ToDoOpenHelper.TODO_TABLE_NAME ,ToDoOpenHelper.TODO_ID + "=" + toDoArrayList.get(position).id,null);
                        toDoArrayList.remove(position);
                        Toast.makeText(MainActivity.this, "Task removed", Toast.LENGTH_SHORT).show();
                        toDoAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        buttonQuickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuickTaskAdd();
            }
        });

        updateToDoList();
    }

    public void updateToDoList(){
        toDoArrayList.clear();
        ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getToDoOpenHelperInstance(MainActivity.this);
        SQLiteDatabase database = toDoOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ToDoOpenHelper.TODO_TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(ToDoOpenHelper.TODO_ID));
            String title = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_TITLE));
            String category = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_DATE));
            String time = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_TIME));
            ToDoItems t = new ToDoItems(id,title,category,date,time);
            toDoArrayList.add(t);
        }
        toDoAdapter.notifyDataSetChanged();
    }

    public void QuickTaskAdd(){

        String quickTask = quickEditTextView.getText().toString();
        if(quickTask.trim().isEmpty()){
            quickEditTextView.setError("Task cannot be empty");
            return;
        }
        ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getToDoOpenHelperInstance(MainActivity.this);
        SQLiteDatabase database = toDoOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ToDoOpenHelper.TODO_TITLE,quickTask);
        database.insert(ToDoOpenHelper.TODO_TABLE_NAME, null, cv);
        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        quickEditTextView.setText("");
        updateToDoList();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                updateToDoList();
            }
            else if(resultCode == RESULT_CANCELED){

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item =spinnerSort.getSelectedItem().toString();
        filter(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void filter( String newText){
        ToDoAdapter.filter(newText);
    }
}
