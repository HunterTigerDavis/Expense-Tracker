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

public class SettingsActivity extends AppCompatActivity {

    private static String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        System.out.println("USERNAME: " + username);
        password = intent.getStringExtra("PASSWORD");
        System.out.println("PASSWORD: " + password);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.root_preferences);
            // TODO: Check if pass is null, in which case we don't have the right username

            PreferenceManager.getDefaultSharedPreferences(getContext()).getString("username", null);
            String u = LoginActivity.getDefaults("username", getContext());
            String p = LoginActivity.getDefaults("password", getContext());
            bindPreferenceSummaryToValue(findPreference("username"));
            bindPreferenceSummaryToValue(findPreference("password"));
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

        private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener
                = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                // List Preference
                if (preference instanceof ListPreference){

                }
                // EditText Preference
                else if (preference instanceof EditTextPreference){
                    if (preference.getKey().equals("key_gallery_name")){
                        // update the changed gallery name to summary field
                        preference.setSummary(stringValue);
                    }

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


    }
}