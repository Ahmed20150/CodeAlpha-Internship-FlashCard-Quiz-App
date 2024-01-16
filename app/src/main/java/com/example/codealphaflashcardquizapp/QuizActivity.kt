package com.example.codealphaflashcardquizapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.TextView
import android.app.AlertDialog
import android.os.Looper
import android.os.Handler
import android.widget.EditText

class QuizActivity : AppCompatActivity() {
    private lateinit var questionsTextView: TextView
    private lateinit var answerTextView: EditText
    private lateinit var incorrectAnswerAnimation: ObjectAnimator
    private lateinit var correctAnswerAnimation: ObjectAnimator
    private var score=0
    var i = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionsTextView= findViewById(R.id.questionText)
        answerTextView = findViewById(R.id.answerQuizInput)

        val backbtn: Button = findViewById(R.id.backButton)
        val nextbtn: Button = findViewById(R.id.nextButton)

        var Questions= Questions;

        incorrectAnswerAnimation = ObjectAnimator.ofFloat(questionsTextView, View.TRANSLATION_X, -10f, 10f)
        incorrectAnswerAnimation.duration = 100
        incorrectAnswerAnimation.interpolator = AccelerateDecelerateInterpolator()
        incorrectAnswerAnimation.repeatCount = 5

        incorrectAnswerAnimation.addUpdateListener {
            questionsTextView.setTextColor(Color.RED)
        }

        correctAnswerAnimation = ObjectAnimator.ofFloat(questionsTextView, View.TRANSLATION_Y, 0f, -50f, 0f)
        correctAnswerAnimation.duration = 1000
        correctAnswerAnimation.interpolator = BounceInterpolator()

        correctAnswerAnimation.addUpdateListener {
            questionsTextView.setTextColor(Color.GREEN)
        }




//        val shuffledQuestions: List<MainActivity.QuizItem> = Questions.shuffle()

        questionsTextView.text= Questions[i].question
        backbtn.setOnClickListener {
            backButton()

        }

        nextbtn.setOnClickListener {
            nextButton()
        }
    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }


    fun backButton() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun nextButton(){
    if(answerTextView.text.isEmpty()){
    showAlert("No Answer Detected", "Please Enter an Answer")
    }
    else{
        if((answerTextView.text.toString().lowercase())==Questions[i].answer.lowercase()){
            score++;
            correctAnswerAnimation.start()
        }
        else{
            incorrectAnswerAnimation.start()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (i<Questions.size){
                i++;
                questionsTextView.text= Questions[i].question
                questionsTextView.setTextColor(Color.BLACK)
                answerTextView.text.clear()

            }
            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }, 2000)

    }


    }
}