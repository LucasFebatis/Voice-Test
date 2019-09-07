package com.febatis.voicetests

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_text.*

import java.util.Locale

class TextToSpeechActivity : AppCompatActivity() {

    private var textToSpeech: TextToSpeech? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        btnToSpeech.setOnClickListener {
            val intent = Intent(applicationContext, SpeechToTextActivity::class.java)
            startActivity(intent)
        }

        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val ttsLang = textToSpeech!!.setLanguage(Locale("pt", "BR"))

                if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The Language is not supported!")
                } else {
                    Log.i("TTS", "Language Supported.")
                }
                Log.i("TTS", "Initialization success.")
            } else {
                Toast.makeText(applicationContext, "TTS Initialization failed!", Toast.LENGTH_SHORT).show()
            }
        })

        btn.setOnClickListener {
            val data = et.text.toString()
            Log.i("TTS", "button clicked: $data")
            val speechStatus = textToSpeech!!.speak(data, TextToSpeech.QUEUE_FLUSH, null)

            if (speechStatus == TextToSpeech.ERROR) {
                Log.e("TTS", "Error in converting Text to Speech!")
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
    }
}