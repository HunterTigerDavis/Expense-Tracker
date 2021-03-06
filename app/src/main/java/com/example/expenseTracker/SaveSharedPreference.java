package com.example.expenseTracker;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;


/**
 * This class is used to check if a user is logged in, i.e. persistent login or "remember me"
 */
public class SaveSharedPreference {

    static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the login status in SharedPreferences
     */
    public static void setLoggedIn(Context context, boolean loggedIn){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(PreferencesUtility.LOGGED_IN_PREF, loggedIn);
        // Set which user is logged in
        getPreferences(context).edit().putString("user_logged", WelcomeActivity.username);
        getPreferences(context).edit().putString("pass_logged", WelcomeActivity.password);
        editor.apply();
    }

    /**
     * Get the current Login status
     */
    public static boolean getLoggedStatus(Context context){
        System.out.println("USER LOGGED IN: " + getPreferences(context).getString("user_logged", "<unset>"));
        System.out.println("PASS LOGGED IN: " + getPreferences(context).getString("pass_logged", "<unset>"));

        return getPreferences(context).getBoolean(PreferencesUtility.LOGGED_IN_PREF, false);
    }
}
