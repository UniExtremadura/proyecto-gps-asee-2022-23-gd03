package es.unex.parsiapp.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import es.unex.parsiapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}