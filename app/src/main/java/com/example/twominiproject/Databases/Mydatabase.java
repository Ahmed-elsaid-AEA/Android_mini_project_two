package com.example.twominiproject.Databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.twominiproject.DataClasses.AdminClass;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Mydatabase extends SQLiteAssetHelper {
    private final static String DATABASE_NAME = "two.db";
    private final static int DATABASE_VERSION =1;
    private final static String USERS_TABLE_NAME = "Users";
    private final static String USERS_TABLE_COLUMN_ID = "id";
    private final static String USERS_TABLE_COLUMN_NAME = "name";
    private final static String USERS_TABLE_COLUMN_PASSWORD = "password";
    private Context context;

    public Mydatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
//        db.execSQL("UPDATE "+USERS_TABLE_COLUMN_ID+" SET seq = 1000 WHERE name = "+USERS_TABLE_NAME);
        onCreate(db);
        if (db.isOpen()) {
            db.close();
        }
    }

    public boolean InsertAdmins(AdminClass admin) {
        long inserted = -1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USERS_TABLE_COLUMN_NAME, admin.getName());
            contentValues.put(USERS_TABLE_COLUMN_PASSWORD, admin.getPassword());
            db.insert(USERS_TABLE_NAME, null, contentValues);
            if (db.isOpen()) {
                db.close();
            }
        } catch (Exception ex) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
        return (inserted > -1) ? true : false;
    }

    public ArrayList<AdminClass> getAllAdminData() {
        ArrayList<AdminClass> admin = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " ; ", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(USERS_TABLE_COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(USERS_TABLE_COLUMN_NAME+""));
                    String password = cursor.getString(cursor.getColumnIndex(USERS_TABLE_COLUMN_PASSWORD));
                    AdminClass adminClass = new AdminClass(id, name, password);
                    admin.add(adminClass);
                } while (cursor.moveToNext());
                cursor.close();

            }
            if (db.isOpen()) {
                db.close();
            }
        } catch (Exception ex) {
            Toast.makeText(context, "ahmed" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return admin;
    }

    public long getallUsersCount() {
        SQLiteDatabase db = getReadableDatabase();
        long i = DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);// if you want all coulmn
        if (db.isOpen()) {
            db.close();
        }  return i;
    }

    public long IsExcitedBYName(AdminClass adminClass) {
        String args[] = new String[]{adminClass.getName()};
        SQLiteDatabase db = getReadableDatabase();
        long i = DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME, USERS_TABLE_COLUMN_NAME + "=?", args);// if you want all coulmn
        if (db.isOpen()) {
            db.close();
        }
        return i;
    }

    public boolean DELETE_UsersByID_(AdminClass adminClass) {
        long inserted = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            //second way
            String id= String.valueOf(adminClass.getId());
            String args[] = new String[]{id};
            //if gretter than 0 is updated
            inserted = db.delete(USERS_TABLE_NAME, null, null);


            if (db.isOpen()) {
                db.close();
            }
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //if gretter than 0 is deleted
        return (inserted > 0) ? true : false;
    }


    public boolean checkExist(AdminClass adminClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String args[] = new String[]{adminClass.getName(), adminClass.getPassword()};
        Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_TABLE_COLUMN_NAME + "=?" + " AND " + USERS_TABLE_COLUMN_PASSWORD + "=?", args);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }
}
