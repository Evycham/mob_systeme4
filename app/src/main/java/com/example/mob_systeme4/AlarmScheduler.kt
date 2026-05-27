package com.example.mob_systeme4

import android.app.AlarmManager
import android.app.PendingIntent
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.format.DateFormat
import java.util.Date

object AlarmScheduler {
    private const val ALARM_REQUEST_CODE = 1001

    /**
     * Prueft, ob exakte Alarme geplant werden duerfen.
     *
     * @param context App-Kontext.
     * @return `true`, wenn exakte Alarme erlaubt sind, sonst `false`.
     */
    fun canScheduleExactAlarm(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return alarmManager.canScheduleExactAlarms()
    }

    /**
     * Plant einen exakten Alarm zur angegebenen Zeit.
     *
     * @param context App-Kontext.
     * @param triggerAtMillis Zielzeit in Millisekunden (Unix-Zeit).
     * @return `true`, wenn der Alarm gesetzt wurde, sonst `false`.
     */
    @SuppressLint("ScheduleExactAlarm")
    fun scheduleExactAlarm(context: Context, triggerAtMillis: Long): Boolean {
        if (!canScheduleExactAlarm(context)) return false

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = alarmPendingIntent(context)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        return true
    }

    /**
     * Erstellt das PendingIntent fuer den AlarmReceiver.
     *
     * @param context App-Kontext.
     * @return PendingIntent fuer den Broadcast.
     */
    fun alarmPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Formatiert eine Uhrzeit passend zu den Systemeinstellungen.
     *
     * @param context App-Kontext.
     * @param timeInMillis Zeit in Millisekunden.
     * @return Formatierte Uhrzeit als String.
     */
    fun formatTime(context: Context, timeInMillis: Long): String {
        val formatter = DateFormat.getTimeFormat(context)
        return formatter.format(Date(timeInMillis))
    }
}
