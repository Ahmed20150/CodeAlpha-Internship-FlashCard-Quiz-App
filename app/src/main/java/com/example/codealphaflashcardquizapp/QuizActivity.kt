package com.example.codealphaflashcardquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class QuizActivity : AppCompatActivity() {
    private lateinit var questionsTextView: TextView
    var i = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionsTextView= findViewById(R.id.questionText)

        val backbtn: Button = findViewById(R.id.backButton)
        val nextbtn: Button = findViewById(R.id.nextButton)

        var Questions= Questions;


//        val shuffledQuestions: List<MainActivity.QuizItem> = Questions.shuffle()

        questionsTextView.text= Questions[i].question
        backbtn.setOnClickListener {
            backButton()
        }

        nextbtn.setOnClickListener {
            nextButton()
        }
    }


    fun backButton() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun nextButton(){
    if (i<Questions.size){
        i++;
        questionsTextView.text= Questions[i].question

    }
    else{
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    }
}