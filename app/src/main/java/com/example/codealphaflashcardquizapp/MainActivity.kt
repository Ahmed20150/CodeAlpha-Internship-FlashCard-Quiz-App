package com.example.codealphaflashcardquizapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
var Questions=mutableListOf<MainActivity.QuizItem>()
class MainActivity : AppCompatActivity() {
    var questionCounter = 1;
    private lateinit var questionInput: EditText
    private lateinit var answerInput: EditText
    private lateinit var flashcardsTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        questionInput = findViewById(R.id.questionInput)
        flashcardsTextView = findViewById(R.id.flashcards)
        answerInput = findViewById(R.id.answerInput)

        val createFlashcardButton: Button = findViewById(R.id.createFlashcard)
        val quizButton: Button = findViewById(R.id.quiz)
        val deleteButton: Button = findViewById(R.id.delete)
        val historyButton: Button = findViewById(R.id.history)

        createFlashcardButton.setOnClickListener {
            createFlashcard()
        }

        quizButton.setOnClickListener {
            goToQuiz()
        }

        deleteButton.setOnClickListener {
            deleteAllQuestions()
        }

        historyButton.setOnClickListener {
            redirectHistory()
        }

        flashcardsTextView.text = retrieveSavedText()
        Questions=retrieveQuestions()
        Log.d("MYLISTTEST2",Questions.toString())

    }


    public class QuizItem(val question: String, val answer: String){
        override fun toString(): String {
            return "QuizItem(question='$question', answer='$answer')"
        }
    }


    fun createFlashcard() {
        val questionText = questionInput.text.toString()
        val answerText = answerInput.text.toString()
        if(questionText.isEmpty()){
            showAlert("Enter Valid Question", "Please Enter a Valid Question Input")
        }
        else if(answerText.isEmpty()){
            showAlert("Enter Valid Answer", "Please Enter a Valid Answer Input")
        }
        else {

            val newQuizItem = QuizItem(questionText, answerText)


            Questions.add(newQuizItem)


//            saveQuestionsToSharedPreferences()
            saveQuestionsListToSharedPreferences(Questions)


            flashcardsTextView.text = retrieveSavedText()
            Questions=retrieveQuestions()


            questionInput.text.clear()
            answerInput.text.clear()

            Log.d("MYLIST",Questions.toString())
        }
    }

//    private fun saveQuestionsToSharedPreferences() {
//        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//
//        val questionsJson = Gson().toJson(Questions)
//
//
//        editor.putString("questionsList", questionsJson)
//        editor.apply()
//    }

    private fun saveQuestionsListToSharedPreferences(questions: MutableList<QuizItem>) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        val jsonAdapter = Gson().getAdapter(object : TypeToken<MutableList<QuizItem>>() {})
        val questionsJson = jsonAdapter.toJson(questions)

        editor.putString("questionsList", questionsJson)
        editor.apply()
    }

    private fun retrieveSavedText(): String {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val questionsJson = sharedPreferences.getString("questionsList", "")


        val savedQuestions: MutableList<QuizItem> =
            Gson().fromJson(questionsJson, object : TypeToken<MutableList<QuizItem>>() {}.type)
                ?: mutableListOf()


        return savedQuestions.joinToString("\n\n") { "Q:${it.question} A:${it.answer}" }
    }

    private fun retrieveQuestions(): MutableList<QuizItem> {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val questionsJson = sharedPreferences.getString("questionsList", "")


        return Gson().fromJson(questionsJson, object : TypeToken<MutableList<QuizItem>>() {}.type)
            ?: mutableListOf()
    }



    fun goToQuiz() {
//        setContentView(R.layout.quiz)
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }

    fun deleteAllQuestions() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("questionsList")
        editor.apply()

        Questions.clear()
        flashcardsTextView.text = ""

        Log.d("MYLISTTEST3", Questions.toString() )
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

    fun redirectHistory(){
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }






}