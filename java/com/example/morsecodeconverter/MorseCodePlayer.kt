package com.example.morsecodeconverter

import android.content.Context
import android.media.SoundPool

class MorseCodePlayer(context: Context) {
    private val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(1).build()
    private val dotSoundId: Int = soundPool.load(context, R.raw.dot_sound, 1)
    private val dashSoundId: Int = soundPool.load(context, R.raw.dash_sound, 1)

    fun playMorseCode(morseCode: String) {
        Thread {
            for (char in morseCode) {
                when (char) {
                    '.' -> playDot()
                    '-' -> playDash()
                }
                Thread.sleep(500)  // Adjust this sleep duration as necessary
            }
            release()
        }.start()
    }


    private fun playDot() {
        soundPool.play(dotSoundId,1f,1f,1,0,1f)
    }
    private fun playDash() {
        soundPool.play(dashSoundId,1f,1f,1,0,1f)
    }
    private fun release() {
        soundPool.release()
    }
}

