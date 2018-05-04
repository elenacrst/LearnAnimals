package com.example.absolute.learnanimals.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.absolute.learnanimals.R;

/**
 * Created by Absolute on 4/17/2018.
 */

public class PrefUtils {

    private Activity mActivity;
    private static final String SHARED_PREF_SETTING="setup_data";
    private static final String PREF_LANG ="lang";

    private static final String PREF_VOLUME = "vol";

    public PrefUtils(Activity activity){
        mActivity = activity;
    }

    public void saveLanguageRes(int res){
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(SHARED_PREF_SETTING,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_LANG, res);
        editor.apply();
    }

    public int getLanguageRes(){
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(SHARED_PREF_SETTING,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREF_LANG,R.drawable.gb);
    }

    public void saveVolume(int volume){
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(SHARED_PREF_SETTING,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_VOLUME, volume);
        editor.apply();
    }

    public int getVolume(){
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(SHARED_PREF_SETTING,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREF_VOLUME, 50);
    }
}
