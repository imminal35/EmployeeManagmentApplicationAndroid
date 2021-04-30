package com.example.employeedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText e_id, e_name, e_location;
    Button add, view, delete, edit, viewAll;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e_id = findViewById(R.id.number);
        e_name = findViewById(R.id.name);
        e_location = findViewById(R.id.location);

        add = findViewById(R.id.btn_add);
        view = findViewById(R.id.btn_view);
        edit = findViewById(R.id.btn_edit);
        delete = findViewById(R.id.btn_delete);

        viewAll = findViewById(R.id.btn_viewAll);

        db = new DbHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empId = e_id.getText().toString();
                String empName = e_name.getText().toString();
                String empLocation = e_location.getText().toString();
                if (empId.length() > 0) {
                    Boolean isDataEntered = db.insertEmployeeData(empId, empName, empLocation);
                    if (isDataEntered == true)
                        Toast.makeText(MainActivity.this, "Employee is added successfully!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Employee ID already exists.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Employee Id is mandatory field to add employee", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empId = e_id.getText().toString();
                String empName = e_name.getText().toString();
                String empLocation = e_location.getText().toString();
                if (empId.length()>0){
                    Boolean isDataUpdated = db.editEmployeeData(empId, empName, empLocation);
                if (isDataUpdated == true)
                    Toast.makeText(MainActivity.this, "Updated information is stored successfully.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Employee Id is mandatory to update information", Toast.LENGTH_SHORT).show();
            }
                else{
                    Toast.makeText(MainActivity.this, "No such employee exists to update information. Verify Employee ID.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empId = e_id.getText().toString();
                if (empId.length() > 0) {
                    Boolean isDataUpdated = db.deleteEmployeeData(empId);
                    if (isDataUpdated == true)
                        Toast.makeText(MainActivity.this, "Employee with ID "+ empId+ " is deleted successfully!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "No such employee exists to delete. Verify Employee ID.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Employee Id is mandatory.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empId = e_id.getText().toString();
                //SQLiteDatabase employeeDB = getApplicationContext().openOrCreateDatabase("Employee.db", Context.MODE_PRIVATE, null);
                Cursor cursor= db.getEmployee(empId);
                if(cursor.getCount()==0){
                    Toast.makeText(getApplicationContext(), "No such employee exists!", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer string =new StringBuffer();
                while(cursor.moveToNext()) {
                    string.append("Employee Id: " + cursor.getString(0) + "\n");
                    string.append("Employee Name: " + cursor.getString(1) + "\n");
                    string.append("Employee Location: " + cursor.getString(2) + "\n\n");
                }
                Toast.makeText(getApplicationContext(), "Employee Details are: \n"+ string.toString(), Toast.LENGTH_LONG).show();
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor= db.getAllEmployee();
                if(cursor.getCount()==0){
                    Toast.makeText(getApplicationContext(), "No Employee exists.", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer string =new StringBuffer();
                while(cursor.moveToNext()) {
                    string.append("Employee Id: " + cursor.getString(0) + "\n");
                    string.append("Employee Name: " + cursor.getString(1) + "\n");
                    string.append("Employee Location: " + cursor.getString(2) + "\n\n");
                }
                Toast.makeText(getApplicationContext(), "Employee Details are: \n"+ string.toString(), Toast.LENGTH_LONG).show();
            }
        });



    }
}