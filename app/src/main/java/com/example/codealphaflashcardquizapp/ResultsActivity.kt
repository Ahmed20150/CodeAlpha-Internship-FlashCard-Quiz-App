package com.example.codealphaflashcardquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultsActivity: AppCompatActivity(){
    private lateinit var scoreTextView: TextView
    private lateinit var percentageTextView: TextView

    companion object{
        var QuizCounter = 0;
        var score=0
        var totalscore=0
        var percentage=0.0

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val test = QuizActivity.score

        QuizCounter++;

        scoreTextView= findViewById(R.id.scoreText)
        percentageTextView= findViewById(R.id.percentageText)
        val homebtn: Button = findViewById(R.id.homeButton)
        val historybtn: Button = findViewById(R.id.historyButton)

        setScoreValues()

        homebtn.setOnClickListener {
            redirectHome()
        }

        historybtn.setOnClickListener {
            redirectHistory()
        }

    }


    fun setScoreValues(){
        score = QuizActivity.score
        totalscore= Questions.size
        percentage= (score.toDouble()/totalscore.toDouble()) * 100
        var scoreText = score.toString()+"/"+totalscore.toString()
        var percText= "Thats "+percentage.toString()+"% !"

        scoreTextView.text= scoreText
        percentageTextView.text= percText

    }


    fun redirectHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun redirectHistory(){
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }


}
