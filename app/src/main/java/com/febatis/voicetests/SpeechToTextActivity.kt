package com.febatis.voicetests

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import kotlinx.android.synthetic.main.activity_speech.*

class SpeechToTextActivity : Activity() {
    private val REQUEST_SPEECH_RECOGNIZER = 3000
    private val mQuestion = "Which company is the largest online retailer on the planet?"
    private var mAnswer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech)

        btn.setOnClickListener {
            startSpeechRecognizer()
        }

        btnToText.setOnClickListener {
            val intent = Intent(applicationContext, TextToSpeechActivity::class.java)
            startActivity(intent)
        }

    }

    private fun startSpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, mQuestion)
        startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER)
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SPEECH_RECOGNIZER) {
            if (resultCode == Activity.RESULT_OK) {
                val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                mAnswer = results!![0]

                if (mAnswer.toUpperCase().indexOf("AMAZON") > -1)
                    textView.text = "\n\nQuestion: " + mQuestion +
                            "\n\nYour answer is '" + mAnswer +
                            "' and it is correct!"
                else
                    textView.text = "\n\nQuestion: " + mQuestion +
                            "\n\nYour answer is '" + mAnswer +
                            "' and it is incorrect!"
            }
        }
    }
}