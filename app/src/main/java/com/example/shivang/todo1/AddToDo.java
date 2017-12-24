package com.example.shivang.todo1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Locale;

public class AddToDo extends AppCompatActivity {
    EditText etDate;
    EditText etTime;
    EditText etDescription;
    EditText etTitle;
    Calendar myCalendar;
    LinearLayout picker;
    SwitchCompat showLayout;
    Spinner sCategory;
    String title;
    String description;
    String date1;
    String time;
    String category;
    Boolean setAlarm;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        database = AppDatabase.getDatabase(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myCalendar = Calendar.getInstance();
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        picker = findViewById(R.id.picker);
        showLayout = findViewById(R.id.show_layout);
        sCategory = findViewById(R.id.sCategory);
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item,getResources().getStringArray(R.array.category));
        //Setting the ArrayAdapter data on the Spinner
        sCategory.setAdapter(aa);
        showLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide layouts if VISIBLE
                if(picker.getVisibility() == View.VISIBLE)
                {
                    picker.setVisibility(View.GONE);
                }
                // Show layouts if they're not VISIBLE
                else
                {
                    picker.setVisibility(View.VISIBLE);
                }
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddToDo.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddToDo.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(TextUtils.isEmpty(etTitle.getText())){
                    title = "";
                }
                else {
                    title = etTitle.getText().toString();
                }
                if(TextUtils.isEmpty(etDescription.getText())){
                    description = "";
                }
                else {
                    description = etDescription.getText().toString();
                }
                if(TextUtils.isEmpty(etDescription.getText())){
                    date1 = "";
                }
                else {
                    date1 = etDate.getText().toString();
                }
                if(TextUtils.isEmpty(etDescription.getText())){
                    time = "";
                }
                else {
                    time = etTime.getText().toString();
                }
                SwitchCompat ss = findViewById(R.id.show_layout);
                ss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setAlarm = isChecked;
                    }
                });
                Spinner sc = findViewById(R.id.sCategory);
//                sc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        switch(position) {
//                            case 0: category = "H";
//                                break;
//                            case 1: category = "W";
//                                break;
//                            case 2: category = "C";
//                                break;
//                            case 3: category = "T";
//                                break;
//                            case 4: category = "P";
//                                break;
//                        }
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
                String text = sc.getSelectedItem().toString();
                category = text.charAt(0) + "";
                ToDo cur = new ToDo(title,description,category,date1,time,setAlarm);
                database.todoDao().addTodo(cur);
                Intent i = new Intent(AddToDo.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to discard the changes ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddToDo.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Do nothing
            }
        });
        builder.show();
    }
}
