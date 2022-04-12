package com.example.vis.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.vis.DBManager;

import java.util.ArrayList;

public class UserModel {
    public long _id = -1;
    public String email;
    public String password;
    public String projectName;
    public String slots;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public UserModel(String project, String slot, String empty) {
        this.projectName = project;
        this.slots = slot;
    }

    @SuppressLint("Range")
    public UserModel(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(DBManager.FLD_ID));
        projectName = cursor.getString(cursor.getColumnIndex(DBManager.FLD_PROJECT_NAME));
        slots = cursor.getString(cursor.getColumnIndex(DBManager.FLD_SLOTS));

    }

    //-----Saving records into database-------
    public long saveRecord() {
        ContentValues values = new ContentValues();
        values.put(DBManager.FLD_EMAIL, email);
        values.put(DBManager.FLD_PASSWORD, password);
        return DBManager.getInstance().insert(DBManager.TBL_USER_INFO, null, values);
    }

    //-----Saving records into database-------
    public long saveProjectRecord() {
        ContentValues values = new ContentValues();
        values.put(DBManager.FLD_PROJECT_NAME, projectName);
        values.put(DBManager.FLD_SLOTS, slots);
        return DBManager.getInstance().insert(DBManager.TBL_PROJECT, null, values);
    }

    public static ArrayList<UserModel> getRecordsList() {
        ArrayList<UserModel> dataList = new ArrayList<>();
        Cursor cursor = DBManager.getInstance().rawQuery("SELECT * FROM " + DBManager.TBL_PROJECT, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    UserModel model = new UserModel(cursor);
                    dataList.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return dataList;
    }
}
