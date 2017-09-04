package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class PrefsMenuActivity extends Activity {
    public static final String USERNAME = "uname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs_menu);
    }

    // Fragmento que mostra a preference com username
    public static class UserPreferenceFragment extends PreferenceFragment {

        protected static final String TAG = "UserPrefsFragment";
        private SharedPreferences.OnSharedPreferenceChangeListener mListener;
        private Preference mUserNamePreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Carrega preferences a partir de um XML
            addPreferencesFromResource(R.xml.user_prefs);

            // pega a Preference especifica do username
            mUserNamePreference = (Preference) getPreferenceManager()
                    .findPreference(USERNAME);

            // Define um listener para atualizar descricao ao modificar preferences
            mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    mUserNamePreference.setSummary(sharedPreferences.getString(
                            USERNAME, "Nada ainda"));
                }
            };

            // Pega objeto SharedPreferences gerenciado pelo PreferenceManager para este Fragmento
            SharedPreferences prefs = getPreferenceManager()
                    .getSharedPreferences();

            // Registra listener no objeto SharedPreferences
            prefs.registerOnSharedPreferenceChangeListener(mListener);

            // Invoca callback manualmente para exibir username atual
            //mListener.onSharedPreferenceChanged(prefs, USERNAME);
			/**/


        }
    }
}