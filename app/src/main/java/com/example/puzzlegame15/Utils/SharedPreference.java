package com.example.puzzlegame15.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {


    private static  final String SHARED_PREF_NAME = "SharePref";
    private static  final String LAST_STEP = "lastStep";
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        preference = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    private  void saveLastStep(int steps){
        editor.putInt(LAST_STEP,steps).commit();
    }

    private int getLastStep(){
        return preference.getInt(LAST_STEP, 0);

    }
}
