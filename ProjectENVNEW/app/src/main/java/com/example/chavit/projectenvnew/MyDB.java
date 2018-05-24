package com.example.chavit.projectenvnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chavit on 9/4/2561.
 */

public class MyDB extends SQLiteOpenHelper {
    public static final String Table_Data = "Information";
    private static final String Database_Name = "ProjectENV";
    private static final int Database_Version = 1;
    private static final String Create_Table = "CREATE TABLE " + Table_Data +
            "(Date TEXT(15)," +
            "Temp TEXT(5)," +
            "Humi TEXT(5),"+
            "Gas TEXT(6),"+
            "Dust TEXT(6),"+
            "Status TEXT(10),"+
            "Latitude TEXT(15),"+
            "Longitude TEXT(15),"+
            "ID TEXT(3))";

    public MyDB(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<HashMap<String, String>> SelectData() {
        try {
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> data;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT strftime('%d-%m-%Y %H:%M',Date) as Date,Temp,Humi,Gas,Dust,Status,Latitude,Longitude FROM " + Table_Data + " ORDER BY Date DESC ";   //SELECT * FROM table ORDER BY column DESC LIMIT 1;
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        data = new HashMap<String, String>();
                        data.put("Date", cursor.getString(0));
                        data.put("Temp", cursor.getString(1));
                        data.put("Humi", cursor.getString(2));
                        data.put("Gas", cursor.getString(3));
                        data.put("Dust", cursor.getString(4));
                        data.put("Status", cursor.getString(5));
                        data.put("Latitude", cursor.getString(6));
                        data.put("Longitude", cursor.getString(7));
                        MyArrList.add(data);
                    } while (cursor.moveToNext());  //while (cursor.moveToNext());
                }
            }
            cursor.close();
            return MyArrList;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<HashMap<String, String>> SelectData2(String Day) {
        try {
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> data;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  * FROM " + Table_Data + " where cast(julianday('now')-julianday(Date) as int)<" + Day;   //SELECT * FROM table ORDER BY column DESC LIMIT 1;
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        data = new HashMap<String, String>();
                        data.put("Date", cursor.getString(0));
                        data.put("Temp", cursor.getString(1));
                        data.put("Humi", cursor.getString(2));
                        data.put("Gas", cursor.getString(3));
                        data.put("Dust", cursor.getString(4));
                        data.put("Status", cursor.getString(5));
                        data.put("Latitude", cursor.getString(6));
                        data.put("Longitude", cursor.getString(7));
                        MyArrList.add(data);
                    } while (cursor.moveToNext());  //while (cursor.moveToNext());
                }
            }
            cursor.close();
            return MyArrList;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<HashMap<String, String>> SelectDataAll() {
        try {
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> data;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  * FROM " + Table_Data ;   //SELECT * FROM table ORDER BY column DESC LIMIT 1;
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        data = new HashMap<String, String>();
                        data.put("Date", cursor.getString(0));
                        data.put("Temp", cursor.getString(1));
                        data.put("Humi", cursor.getString(2));
                        data.put("Gas", cursor.getString(3));
                        data.put("Dust", cursor.getString(4));
                        data.put("Status", cursor.getString(5));
                        data.put("Latitude", cursor.getString(6));
                        data.put("Longitude", cursor.getString(7));
                        MyArrList.add(data);
                    } while (cursor.moveToNext());  //while (cursor.moveToNext());
                }
            }
            cursor.close();
            return MyArrList;
        } catch (Exception e) {
            return null;
        }

    }

    public long InData(String Date ,String Temp, String Humi, String Gas, String Dust, String Status,String Latitude, String Longitude ) {
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data

            ContentValues Val = new ContentValues();
            Val.put("Date", Date);
            Val.put("Temp", Temp);
            Val.put("Humi", Humi);
            Val.put("Gas", Gas);
            Val.put("Dust", Dust);
            Val.put("Status", Status);
            Val.put("Latitude", Latitude);
            Val.put("Longitude", Longitude);

            long rows = db.insert(Table_Data, null, Val);

            db.close();
            return rows; // return rows inserted.
        } catch (Exception e) {
            return -1;
        }
    }
    public int getDataCount() {
        try {
            String countQuery = "SELECT  * FROM " + Table_Data;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getDataCount2(String Day) {
        try {
            String countQuery = "SELECT  * FROM " + Table_Data + " where cast(julianday('now')-julianday(Date) as int)<" + Day;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        } catch (Exception e) {
            return 0;
        }
    }

    public void deleteAll() {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("delete from " + Table_Data);
        db.close();

    }
}
