package com.example.mob_systeme4

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        AlarmSoundPlayer.start(this)

        findViewById<Button>(R.id.btnSnooze).setOnClickListener {
            val snoozeMinutes = SettingsActivity.getSnoozeMinutes(this)
            val newTime = System.currentTimeMillis() + (snoozeMinutes * 60_000L)
            val ok = AlarmScheduler.scheduleExactAlarm(this, newTime)
            AlarmSoundPlayer.stop()
            if (ok) {
                NotificationHelper.showSnoozeNotification(this, newTime)
            } else {
                Toast.makeText(this, R.string.exact_alarm_permission_needed, Toast.LENGTH_LONG).show()
            }
            finish()
        }

        findViewById<Button>(R.id.btnDismiss).setOnClickListener {
            AlarmSoundPlayer.stop()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AlarmSoundPlayer.stop()
    }
}
