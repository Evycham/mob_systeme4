package com.example.mob_systeme4

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            findPreference<EditTextPreference>(KEY_SNOOZE_MINUTES)?.setOnBindEditTextListener {
                it.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }
        }
    }

    companion object {
        const val KEY_SNOOZE_MINUTES = "snooze_minutes"
        private const val DEFAULT_SNOOZE = 5

        /**
         * Liest die gespeicherte Snooze-Zeit aus den Preferences.
         *
         * @param context App-Kontext.
         * @return Snooze-Zeit in Minuten (1 bis 120).
         */
        fun getSnoozeMinutes(context: Context): Int {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val raw = prefs.getString(KEY_SNOOZE_MINUTES, DEFAULT_SNOOZE.toString())
            return raw?.toIntOrNull()?.coerceIn(1, 120) ?: DEFAULT_SNOOZE
        }
    }
}
