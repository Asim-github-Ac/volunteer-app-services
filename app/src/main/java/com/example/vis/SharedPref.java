package com.example.vis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPref {
    private Editor mEditor;
    private final int PRIVATE_MODE = 0;
    private SharedPreferences mSharedPref;
    @SuppressLint("StaticFieldLeak")
    private static SharedPref sSharedPref;
    private static final String PREF_NAME = "SharedPref";
    public static final String AD_WIDTH = "ad_width";
    public static final String SHOW_RATE_US = "show_rate_us";
    public static final String TEMP_IMAGE_FILE_NAME = "temp_name";

    public static SharedPref getInstance() {
        if (sSharedPref == null) {
            sSharedPref = new SharedPref();
        }
        return sSharedPref;
    }

    private SharedPref() {
        Context _context = Global.globalContext();
        mSharedPref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPref.edit();
    }

    public void savePref(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void savePref(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void savePref(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void savePref(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public String getStringPref(String key, String defaultVal) {
        return mSharedPref.getString(key, defaultVal);
    }

    public int getIntPref(String key, int defaultVal) {
        return mSharedPref.getInt(key, defaultVal);
    }

    public long getLongPref(String key, int defaultVal) {
        return mSharedPref.getLong(key, defaultVal);
    }

    public boolean getBooleanPref(String key, boolean defaultVal) {
        return mSharedPref.getBoolean(key, defaultVal);
    }

    public void clearStoredData() {
        mEditor.clear();
        mEditor.commit();
    }
}