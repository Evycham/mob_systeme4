# Wecker App (sehr einfache Doku)

## Was kann die App?
- Uhrzeit mit `TimePicker` einstellen
- Alarm setzen
- Alarm abbrechen
- Wenn Alarm klingelt: zweite Seite mit `Snooze`
- Snooze-Zeit in Einstellungen speichern
- Nach Snooze kommt eine Notification mit neuer Zeit
- Beim Klick auf Notification geht es zurück zur Hauptseite

## Wichtige Dateien
- `app/src/main/java/com/example/mob_systeme4/MainActivity.kt`
- `app/src/main/java/com/example/mob_systeme4/AlarmActivity.kt`
- `app/src/main/java/com/example/mob_systeme4/AlarmReceiver.kt`
- `app/src/main/java/com/example/mob_systeme4/AlarmScheduler.kt`
- `app/src/main/java/com/example/mob_systeme4/AlarmSoundPlayer.kt`
- `app/src/main/java/com/example/mob_systeme4/NotificationHelper.kt`
- `app/src/main/java/com/example/mob_systeme4/SettingsActivity.kt`

## Ablauf (kurz)
1. In der Hauptseite Zeit wählen.
2. Auf `Alarm setzen` klicken.
3. Beim Klingeln öffnet sich die Alarm-Seite und Sound läuft.
4. `Snooze` verschiebt den Alarm (Minuten aus Einstellungen).
5. Notification zeigt neue Alarmzeit.
6. `Alarm abbrechen` stoppt Alarm/Sound.

## Snooze einstellen
- In der Hauptseite auf `Snooze-Einstellungen` klicken.
- Minuten eingeben (z. B. `5`).
- Wert wird gespeichert.

## Hinweise
- Sound-Datei liegt in `app/src/main/res/raw/alarm.wav`.
- Für exakte Alarme kann Android eine Freigabe verlangen.
- Notification-Erlaubnis wird bei neueren Android-Versionen angefragt.
