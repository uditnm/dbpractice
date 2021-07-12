package com.example.dbpractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbclass extends SQLiteOpenHelper {

    public static final String CUST_TABLE1 = "CUST_TABLE";
    public static final String COLUMN_CUST_NAME = "CUST_NAME";
    public static final String COLUMN_CUST_AGE = "CUST_AGE";
    public static final String ACTIVE_CUST = "ACTIVE_CUST";
    public static final String COLUMN_ID = "ID";


    public dbclass(@Nullable Context context) {
        super(context, "myDB", null, 1);
    }

    //this is called when db is first accessed. Code to create new db should be here
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable = "CREATE TABLE " + CUST_TABLE1 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUST_NAME + " TEXT, " + COLUMN_CUST_AGE + " INT, " + ACTIVE_CUST + " BOOL)";

        db.execSQL(createtable);
    }

    //this is called if db version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(customerModel customermodel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUST_NAME,customermodel.getName());
        cv.put(COLUMN_CUST_AGE,customermodel.getAge());
        cv.put(ACTIVE_CUST,customermodel.isActive());

        long insert = db.insert(CUST_TABLE1, null, cv);

        if(insert == -1)
            return false;
        else
            return true;
    }

    public List<customerModel> getall(){
        List <customerModel> returnList = new ArrayList<>();

        String query = "SELECT * FROM "+ CUST_TABLE1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            //loop through the cursor (result set) and create new customer objects. put in return list.
            do{
                int custId = cursor.getInt(0);
                String custName = cursor.getString(1);
                int custAge = cursor.getInt(2);
                boolean isActive = cursor.getInt(3)==1?true:false;

                customerModel newcust = new customerModel(custId,custName,custAge,isActive);
                returnList.add(newcust);

            }while(cursor.moveToNext());
        }
        else{
            //fail. Don't add to list.

        }

        //close both cursor and db when done
        cursor.close();
        db.close();
        return returnList;
    }
}
