package com.example.codealphaflashcardquizapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
var makeQuiz = false;
class QuizActivity : AppCompatActivity() {
    private lateinit var questionsTextView: TextView
    private lateinit var correctAnswerTextView: TextView
    private lateinit var answerTextView: EditText
    private lateinit var incorrectAnswerAnimation: ObjectAnimator
    private lateinit var correctAnswerAnimation: ObjectAnimator
    private lateinit var fadeInAnimation: ObjectAnimator

    companion object{
        var score=0
//        var total = Questions.size
    }
    var i = 0;
    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionsTextView= findViewById(R.id.questionText)
        answerTextView = findViewById(R.id.answerQuizInput)
        correctAnswerTextView= findViewById(R.id.correctAnswer)

        val backbtn: Button = findViewById(R.id.backButton)
        val nextbtn: Button = findViewById(R.id.nextButton)

//        var Questions= Questions;

        score=0;

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

        fadeInAnimation = ObjectAnimator.ofFloat(correctAnswerTextView, View.ALPHA, 0f, 1f)
        fadeInAnimation.duration = 600
        fadeInAnimation.interpolator = AccelerateDecelerateInterpolator()
        fadeInAnimation.repeatCount = 0



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
            correctAnswerTextView.text = "Correct Answer: \n ${Questions[i].answer}"
            fadeInAnimation.start()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            correctAnswerTextView.text= ""
            if (i<Questions.size - 1 ){
                i++;
                questionsTextView.text= Questions[i].question
                questionsTextView.setTextColor(Color.BLACK)
                answerTextView.text.clear()

            }
            else{
                makeQuiz = true;
                val intent = Intent(this, ResultsActivity::class.java)
                startActivity(intent)
//                score=0
            }
        }, 2000)

    }


    }
}