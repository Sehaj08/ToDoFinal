package com.example.sehajgulati08.todofinal;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ToDoDetailActivity extends AppCompatActivity {

    EditText titleTextView , dateTextView , timeTextView;
    int id;
    long Date;
    String title , category,date ,time;
    ImageView calenderImageView;
    ArrayList<String> categoryArrayList;
    ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);
        spinner = (Spinner) findViewById(R.id.spinner);
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add("Home");
        categoryArrayList.add("Personal");
        categoryArrayList.add("Shopping");
        categoryArrayList.add("Work");
        categoryArrayList.add("Others");
//        ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getToDoOpenHelperInstance(ToDoDetailActivity.this);
//        SQLiteDatabase database = toDoOpenHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(ToDoOpenHelper.SPINNER_CATEGORY,"Home");
//        contentValues.put(ToDoOpenHelper.SPINNER_CATEGORY,"Personal");
//        contentValues.put(ToDoOpenHelper.SPINNER_CATEGORY,"Shopping");
//        contentValues.put(ToDoOpenHelper.SPINNER_CATEGORY,"Work");
//        contentValues.put(ToDoOpenHelper.SPINNER_CATEGORY,"Others");
//        database.insert(ToDoOpenHelper.SPINNER__TABLE_NAME, null, contentValues);


//        database.insert(ToDoOpenHelper.TODO_TABLE_NAME, null, contentValues);
// Create an ArrayAdapter using the string array and a default spinner layout
         spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryArrayList);
// Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String category = spinner.getItemAtPosition(i).toString();
//                if(category.equals("Custom")){
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(ToDoDetailActivity.this);
//                    builder.setTitle("Custom Category");
//                    builder.setCancelable(false);
//                    View v = getLayoutInflater().inflate(R.layout.category_dialog_view,null);
//                    final EditText categoryEditText = (EditText)v.findViewById(R.id.categoryEditTextView);
//                    builder.setView(v);
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            String categ = categoryEditText.getText().toString();
//                            if(categ.trim().isEmpty()){
//                                categoryEditText.setError("This cant be blank");
//                            }
//                            else {
//                                ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getToDoOpenHelperInstance(ToDoDetailActivity.this);
//                                SQLiteDatabase database = toDoOpenHelper.getWritableDatabase();
//                                ContentValues contentValues = new ContentValues();
//                                contentValues.put(ToDoOpenHelper.SPINNER_CATEGORY,categoryEditText.toString());
//                                database.insert(ToDoOpenHelper.SPINNER__TABLE_NAME, null, contentValues);
//                                categoryArrayList.add(categoryArrayList.size() - 1, categ);
//                                spinnerAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    });
//                   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialogInterface, int i) {
//                           dialogInterface.dismiss();
//                       }
//                   });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        titleTextView = (EditText) findViewById(R.id.detailTitleEditText);
        dateTextView = (EditText) findViewById(R.id.detailDateEditText);
        timeTextView = (EditText)findViewById(R.id.detailTimeEditText);
        calenderImageView = (ImageView) findViewById(R.id.calenderImageView);
        Button submitButton = (Button)findViewById(R.id.submitButton);

        //get ID by intent

        Intent i = getIntent();
        id = i.getIntExtra(IntentConstants.ID,-1);
        // create ToDoOpenHelper object and retrieve the content corresponding to the ID
        ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getToDoOpenHelperInstance(this);
        SQLiteDatabase database = toDoOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * from " + ToDoOpenHelper.TODO_TABLE_NAME + " WHERE " + ToDoOpenHelper.TODO_ID  + " = " + id , null);

        while(cursor.moveToNext()){
            title = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_TITLE));
            category = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_CATEGORY));
            date = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_DATE));
            time = cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.TODO_TIME));
        }
        cursor.close();

        //set Text of the field in this activity
        titleTextView.setText(title);
        spinner.setSelection(categoryArrayList.indexOf(category));
        dateTextView.setText(date);
        timeTextView.setText(time);

        //setting date by the help of DatePicker

        calenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                showDatePicker(ToDoDetailActivity.this, year, month, 1);
            }
        });
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                showDatePicker(ToDoDetailActivity.this, year, month, 1);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleTextView.getText().toString();
                String newCategory = spinner.getSelectedItem().toString();
                String newDate = dateTextView.getText().toString();
                String newTime = timeTextView.getText().toString();

                if(newTitle.trim().isEmpty()){
                    titleTextView.setError("This field is mandatory");
                    return;
                }

                //create ToDoOpenHelperObject to be able to get data from database
                ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getToDoOpenHelperInstance(ToDoDetailActivity.this);
                SQLiteDatabase database = toDoOpenHelper.getWritableDatabase();

                //put corresponding values into content values
                ContentValues cv = new ContentValues();
                cv.put(ToDoOpenHelper.TODO_TITLE,newTitle);
                cv.put(ToDoOpenHelper.TODO_CATEGORY,newCategory);
                cv.put(ToDoOpenHelper.TODO_DATE,newDate);
                cv.put(ToDoOpenHelper.TODO_TIME,newTime);

                if(id == -1) {
                    database.insert(ToDoOpenHelper.TODO_TABLE_NAME, null, cv);
                    Toast.makeText(ToDoDetailActivity.this, "Task Added", Toast.LENGTH_SHORT).show();

                }else{
                    database.update(ToDoOpenHelper.TODO_TABLE_NAME,cv,ToDoOpenHelper.TODO_ID + "=" + id,null);
                    Toast.makeText(ToDoDetailActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();

                }
                setResult(RESULT_OK);
                finish();
            }
        });

    }
    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        // Creating datePicker dialog object
        // It requires context and listener that is used when a date is selected by the user.

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    //This method is called when the user has finished selecting a date.
                    // Arguments passed are selected year, month and day
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        // To get epoch, You can store this date(in epoch) in database
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        Date = calendar.getTime().getTime();
                        // Setting date selected in the edit text
                        dateTextView.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        //Call show() to simply show the dialog
        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        Toast.makeText(this, "Changes not saved", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
