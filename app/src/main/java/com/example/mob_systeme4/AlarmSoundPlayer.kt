package com.example.mob_systeme4

import android.content.Context
import android.media.MediaPlayer

object AlarmSoundPlayer {

    private var player: MediaPlayer? = null

    fun start(context: Context) {
        if (player?.isPlaying == true) return

        player = MediaPlayer.create(context.applicationContext, R.raw.alarm).apply {
            isLooping = true
            start()
        }
    }

    fun stop() {
        player?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        player = null
    }
}
