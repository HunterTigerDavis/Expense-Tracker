package com.example.expenseTracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

/**
 * This Activity holds the settings screen and allows the user to change their username and password,
 * and see "about" information like the developers, app version, and a help section
 */
public class SettingsActivity extends AppCompatActivity {

    private static String username, password;
    private static String u = "";
    private static String p = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        username = LoginActivity.getDefaults("user_logged", getApplicationContext());;
        System.out.println("USERNAME: " + username);
        password = LoginActivity.getDefaults("pass_logged", getApplicationContext());
        System.out.println("PASSWORD: " + password);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.root_preferences);

            u = LoginActivity.getDefaults("user_logged", getContext());
            p = LoginActivity.getDefaults("pass_logged", getContext());
            System.out.println("USER LOGGED: " + u);
            System.out.println("PASS LOGGED: " + p);

            SharedPreferences sp = getPreferenceManager().getSharedPreferences();
            EditTextPreference editTextPrefUser = (EditTextPreference) findPreference("username");
            assert editTextPrefUser != null;
            editTextPrefUser.setSummary(u);
            editTextPrefUser.setText(u);
            EditTextPreference editTextPrefPass = (EditTextPreference) findPreference("password");
            assert editTextPrefPass != null;
            editTextPrefPass.setSummary(p);
            editTextPrefPass.setText(p);


            // TODO: Write the changed editText to PReferences to save the changed username/password!

            bindPreferenceSummaryToValue(findPreference("username"));
            bindPreferenceSummaryToValue(findPreference("password"));
        }

        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
        }

        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            return super.onOptionsItemSelected(item);
        }

        private static void bindPreferenceSummaryToValue(Preference preference){
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, PreferenceManager
            .getDefaultSharedPreferences(preference.getContext())
            .getString(preference.getKey(), ""));
        }

        /**
         * A preference value change listener that updates the preference's summary to reflect it's
         * new value when changed
         */
        private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener
                = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                // EditText Preference
                if (preference instanceof EditTextPreference){
                    // Set the editText Preference to be the current username and pass
                    SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
                    SharedPreferences.Editor editor = sharePref.edit();
                    if (preference.getKey().equals("username")){
                        preference.setSummary(stringValue);
                        preference.setDefaultValue(stringValue);
                        // u is already saved from old user_logged
                        // Changing the current user logged in:
                        LoginActivity.setDefaults("user_logged", stringValue, preference.getContext());
                        // Removing the old user:
                        editor.remove(u).apply();
                        // Adding in the new user with their same old password and new username
                        LoginActivity.setDefaults(stringValue, p, preference.getContext());
                        System.out.println("Changing username to: " + stringValue);
                    }
                    if (preference.getKey().equals("password")){
                        preference.setSummary(stringValue);
                        preference.setDefaultValue(stringValue);
                        // Changing the password of the user currently logged in:
                        LoginActivity.setDefaults("pass_logged", stringValue, preference.getContext());
                        editor.remove(u).apply();
                        // Adding in new user with their same old username and new password
                        LoginActivity.setDefaults(u, stringValue, preference.getContext());
                        System.out.println("Changing password to: " + stringValue);

                    }

                    // Change the preference default as well

                    //LoginActivity.setDefaults(u, stringValue, preference.getContext());
                    // Get the current user logged in
                }
                // Any other preference
                else{
                    preference.setSummary(stringValue);
                }
                return true;
            }
        };


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            //setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            Preference pref = findPreference(s);
            if (pref instanceof EditTextPreference) {
                EditTextPreference etp = (EditTextPreference) pref;
                pref.setSummary(etp.getText());
            }
        }

    }
}