package com.example.vis;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DBManager {
    ///////////////////////////// Column Names //////////////////////////////
    public static final String FLD_ID = "_id";
    public static final String FLD_EMAIL = "fld_email";
    public static final String FLD_PASSWORD = "fld_password";
    public static final String FLD_DAY = "fld_day";
    public static final String FLD_DATE = "fld_date";
    public static final String FLD_STATUS = "fld_status";

    public static final String FLD_ROLE = "fld_role";
    public static final String FLD_PROJECT_NAME = "fld_project_name";
    public static final String FLD_SLOTS = "fld_slots";



    /////////////////////////// Database And Table Name ///////////////////////////

    private static final String DATABASE_NAME = "vc_db";
    public static final String TBL_USER_INFO = "tbl_user_info";
    public static final String TBL_PROJECT = "tbl_project";
   // public static final String TBL_DEPARTMENT = "tbl_department";


    private static final int DATABASE_VERSION = 1;
    private static final int DEFAULT_DATABASE_VERSION = 1;

    // private static final String CREATE_TBL_EMPLOYEE = "CREATE TABLE "+TBL_EMPLOYEE+ " (_id INTEGER PRIMARY KEY autoincrement, fld_name TEXT, fld_department LONG, fld_gender TEXT, fld_salary INTEGER)";
    //  private static final String CREATE_TBL_DEPARTMENT = "CREATE TABLE "+TBL_DEPARTMENT+ " (_id INTEGER PRIMARY KEY autoincrement, fld_department_name TEXT)";


    private static final String CREATE_TBL_USER = "CREATE TABLE " + TBL_USER_INFO + "(" + FLD_ID + " INTEGER PRIMARY KEY autoincrement,"
            + FLD_EMAIL + " TEXT,"
            + FLD_PASSWORD + " TEXT," + FLD_PROJECT_NAME + " TEXT," + FLD_DAY + " TEXT,"
            + FLD_STATUS + " TEXT," + FLD_DATE + " TEXT" + ")";

    private static final String CREATE_TBL_PROJECT = "CREATE TABLE " + TBL_PROJECT + "(" + FLD_ID + " INTEGER PRIMARY KEY autoincrement,"
            + FLD_PROJECT_NAME + " TEXT, "+ FLD_SLOTS + " TEXT" + ")";


    ///////////////////////// HELPER CLASS TO CREATE DATABASE //////////////////////

    @SuppressLint("StaticFieldLeak")
    private static DBManager mDbManager;
    private static DatabaseHelper sDBHELPER;
    private SQLiteDatabase mSqlDb;

    public static DBManager getInstance() {
        if (mDbManager == null || mDbManager.mSqlDb == null || sDBHELPER == null) {
            mDbManager = new DBManager(Global.globalContext());
//            if (checkDataBase()) {
            mDbManager.mSqlDb = sDBHELPER.getWritableDatabase();
//            }
        }
        return mDbManager;
    }

    private DBManager(Context context) {
        sDBHELPER = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TBL_USER);
            db.execSQL(CREATE_TBL_PROJECT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            db.disableWriteAheadLogging();
        }
    }

    public void close() {
        try {
            if (sDBHELPER != null) {
                sDBHELPER.close();
            }
            sDBHELPER = null;
            SQLiteDatabase.releaseMemory();
        } catch (Exception e) {
            //  FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
    }

  /*  private static boolean checkDataBase() {
        SQLiteDatabase sqlDb = null;
        try {
            String myPath = Global.globalContext().getDatabasePath(DATABASE_NAME).getPath();
            sqlDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (sqlDb != null) {
            sqlDb.close();
        }
        return sqlDb != null;
    }*/


    ////////////////////////////// FUNCTIONS ////////////////////////////////

    public long insert(String table, String nullColumnHack, ContentValues values) {
        long id = -1;
        try {
            id = mSqlDb.insert(table, nullColumnHack, values);
        } catch (SQLiteException sqe) {
            sqe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public long update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        long rows = -1;
        try {
            rows = mSqlDb.update(table, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public long updateOrInsert(String table, String nullColumnHack, ContentValues values, String whereClause, String[] whereArgs) {
        long rows = -1;
        try {
            rows = mSqlDb.update(table, values, whereClause, whereArgs);
            if (rows == 0) {
                rows = mSqlDb.insert(table, nullColumnHack, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = mSqlDb.rawQuery(sql, selectionArgs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public Cursor dbQuery(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = null;
        try {
            cursor = mSqlDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        int rows = -1;
        try {
            rows = mSqlDb.delete(table, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}