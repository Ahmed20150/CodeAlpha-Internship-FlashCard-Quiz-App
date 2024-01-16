package com.example.codealphaflashcardquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultsActivity: AppCompatActivity(){
    private lateinit var scoreTextView: TextView
    private lateinit var percentageTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val test = QuizActivity.score

        scoreTextView= findViewById(R.id.scoreText)
        percentageTextView= findViewById(R.id.percentageText)
        val homebtn: Button = findViewById(R.id.homeButton)

        setScoreValues()

        homebtn.setOnClickListener {
            redirectHome()
        }

    }


    fun setScoreValues(){
        var score = QuizActivity.score
        var totalscore= QuizActivity.total
        var percentage= (score.toDouble()/totalscore.toDouble()) * 100
        var scoreText = score.toString()+"/"+totalscore.toString()
        var percText= "Thats "+percentage.toString()+"% !"

        scoreTextView.text= scoreText
        percentageTextView.text= percText

    }


    fun redirectHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}
