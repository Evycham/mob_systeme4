package com.example.mob_systeme4

import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timePicker = findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)

        findViewById<Button>(R.id.btnSetAlarm).setOnClickListener {
            scheduleAlarmFromPicker()
        }

        findViewById<Button>(R.id.btnCancelAlarm).setOnClickListener {
            cancelAlarm()
        }

        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        requestNotificationPermissionIfNeeded()
    }

    private fun scheduleAlarmFromPicker() {
        if (!AlarmScheduler.canScheduleExactAlarm(this)) {
            openExactAlarmSettings()
            Toast.makeText(this, R.string.exact_alarm_permission_needed, Toast.LENGTH_LONG).show()
            return
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                set(Calendar.HOUR_OF_DAY, timePicker.hour)
                set(Calendar.MINUTE, timePicker.minute)
            } else {
                @Suppress("DEPRECATION")
                set(Calendar.HOUR_OF_DAY, timePicker.currentHour)
                @Suppress("DEPRECATION")
                set(Calendar.MINUTE, timePicker.currentMinute)
            }
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        AlarmScheduler.scheduleExactAlarm(this, calendar.timeInMillis)
        val formattedTime = AlarmScheduler.formatTime(this, calendar.timeInMillis)
        Toast.makeText(this, getString(R.string.alarm_set_message, formattedTime), Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = AlarmScheduler.alarmPendingIntent(this)
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
        AlarmSoundPlayer.stop()

        Toast.makeText(this, R.string.alarm_cancelled, Toast.LENGTH_SHORT).show()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
            1234
        )
    }

    private fun openExactAlarmSettings() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
            data = Uri.parse("package:$packageName")
        }
        startActivity(intent)
    }
}
