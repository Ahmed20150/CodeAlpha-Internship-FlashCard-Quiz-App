package com.example.codealphaflashcardquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val backbtn: Button = findViewById(R.id.backButton)

        backbtn.setOnClickListener {
            backButton()
        }
    }


    fun backButton() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}