package com.example.codealphaflashcardquizapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar
import java.util.Date

var Quizzes=mutableListOf<HistoryActivity.Quiz>()
var QuizLayouts = mutableListOf<TextView>()
class HistoryActivity  : AppCompatActivity()  {
    private lateinit var historyLayout: LinearLayout
    private lateinit var historyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyLayout = findViewById(R.id.historyLayout)
        historyTextView= findViewById(R.id.historyTextView)

        val homebtn: Button = findViewById(R.id.homebtn)
        val deletebtn: Button = findViewById(R.id.delete)

//        updateHistory()

        if(makeQuiz){
            makeQuiz = false;
            createQuiz()
        }

        historyTextView.text= retrieveSavedText()




        homebtn.setOnClickListener {
            redirectHome()
        }

        deletebtn.setOnClickListener {
            deleteHistory()
        }


    }

    public class Quiz(
        val quizno: Int, val score: String, val perc: Double,
        val currDate: Date, val currTime: Long  ){
        override fun toString(): String {
            return "Quizdetails(QuizNo='$quizno', score='$score', Perc='$perc', currDate='$currDate' , currTime='$currTime' )"
        }
    }

    fun redirectHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    fun createQuiz(){
        val currentDate = Calendar.getInstance().time
        val currentTimestamp: Long = System.currentTimeMillis()
        val newQuiz = Quiz(ResultsActivity.QuizCounter, ResultsActivity.score.toString()+"/"+
                ResultsActivity.totalscore.toString(), ResultsActivity.percentage, currentDate , currentTimestamp )
        Quizzes.add(newQuiz)

        saveQuizListToSharedPreferences(Quizzes)

        historyTextView.text= retrieveSavedText()

        Quizzes= retrieveQuizzes()





    }

    private fun setRoundedBackground(view: TextView, cornerRadius: Float) {
        val radii = floatArrayOf(
            cornerRadius, cornerRadius, cornerRadius, cornerRadius,
            cornerRadius, cornerRadius, cornerRadius, cornerRadius
        )

        val roundRectShape = RoundRectShape(radii, null, null)
        val shapeDrawable = ShapeDrawable(roundRectShape)
        shapeDrawable.paint.color = Color.WHITE

        view.background = shapeDrawable
    }

    private fun saveQuizListToSharedPreferences(quizzes: MutableList<Quiz>) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        val jsonAdapter = Gson().getAdapter(object : TypeToken<MutableList<Quiz>>() {})
        val quizzesJson = jsonAdapter.toJson(quizzes)

        editor.putString("quizzesList", quizzesJson)
        editor.apply()
    }

    private fun retrieveSavedText(): String {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val quizzesJson = sharedPreferences.getString("quizzesList", "")


        val savedQuizzes: MutableList<Quiz> =
            Gson().fromJson(quizzesJson, object : TypeToken<MutableList<Quiz>>() {}.type)
                ?: mutableListOf()



        return savedQuizzes.joinToString("\n\n") { "Q${it.quizno}:   Score:${it.score}   " +
                "Percentage:${it.perc}  ${it.currDate}  ${it.currTime} "}
    }

    private fun retrieveQuizzes(): MutableList<Quiz> {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val quizzesJson = sharedPreferences.getString("quizzesList", "")


        return Gson().fromJson(quizzesJson, object : TypeToken<MutableList<Quiz>>() {}.type)
            ?: mutableListOf()
    }

    fun deleteHistory() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("quizzesList")
        editor.apply()

        Quizzes.clear()
        historyTextView.text = ""


    }



//    private fun addTextToContainer(text: String, container: LinearLayout) {
//        val textView = TextView(this)
//        textView.text = text
//        container.addView(textView)
//    }

//    fun updateHistory(){
//
//        var historyText= "Quiz "+ResultsActivity.QuizCounter+ ":"  + ResultsActivity.score
//            .toString()+"/"+ ResultsActivity.totalscore.toString() + "" + ResultsActivity.percentage
////        addTextToContainer(historyText, historyLayout)
//
//        historyTextView.text="${historyTextView.text}\n$historyText"
//
//    }

}