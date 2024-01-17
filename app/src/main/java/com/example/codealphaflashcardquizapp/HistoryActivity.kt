package com.example.codealphaflashcardquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity  : AppCompatActivity()  {
    private lateinit var historyTextView: TextView
    private lateinit var historyLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyTextView = findViewById(R.id.HistoryText)
        historyLayout = findViewById(R.id.historyLayout)

        val homebtn: Button = findViewById(R.id.homeButton)

        updateHistory()


        homebtn.setOnClickListener {
            redirectHome()
        }


    }

    fun redirectHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun updateHistory(){

        var historyText= "Quiz "+ResultsActivity.QuizCounter+ ":"  + ResultsActivity.score.toString()+"/"+ ResultsActivity.totalscore.toString() + "" + ResultsActivity.percentage
//        addTextToContainer(historyText, historyLayout)

        historyTextView.text="${historyTextView.text}\n$historyText"

    }

    private fun addTextToContainer(text: String, container: LinearLayout) {
        val textView = TextView(this)
        textView.text = text
        container.addView(textView)
    }
}