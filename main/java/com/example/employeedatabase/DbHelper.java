package com.example.employeedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "Employee.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create Table EmployeeDetails(id TEXT primary key, name TEXT, location TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("drop table if exists EmployeeDetails");
    }

    public boolean insertEmployeeData(String id, String name, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("location", location);
        long results= db.insert("EmployeeDetails", null, contentValues);
        if(results== -1)
            return false;
        else
            return true;
    }
    public boolean editEmployeeData(String id, String name, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("location", location);
        Cursor cursor= db.rawQuery("select * from EmployeeDetails where id= ?", new String[] {id});
        if(cursor.getCount()>0) {
            long results = db.update("EmployeeDetails", contentValues, "id=?", new String[]{id});
            if (results == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public boolean deleteEmployeeData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("select * from EmployeeDetails where id= ?", new String[] {id});
        if(cursor.getCount()>0) {
            long results = db.delete("EmployeeDetails", "id=?", new String[]{id});
            if (results == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Cursor getAllEmployee(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("select * from EmployeeDetails", null);
        return cursor;
    }

    public Cursor getEmployee(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("select * from EmployeeDetails where id= ?", new String[] {id});
        return cursor;
    }
}
