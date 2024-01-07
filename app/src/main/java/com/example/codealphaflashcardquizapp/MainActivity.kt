package com.example.codealphaflashcardquizapp

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

        createFlashcardButton.setOnClickListener {
            createFlashcard()
        }

        quizButton.setOnClickListener {
            goToQuiz()
        }

        deleteButton.setOnClickListener {
            deleteAllQuestions()
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
    var Questions=mutableListOf<QuizItem>()

    fun createFlashcard() {
        val questionText = questionInput.text.toString()
        val answerText = answerInput.text.toString()

        if (questionText.isNotEmpty()) {
            // Create a new QuizItem
            val newQuizItem = QuizItem(questionText, answerText)

            // Add the new QuizItem to the MutableList
            Questions.add(newQuizItem)

            // Save the updated MutableList in SharedPreferences
            saveQuestionsToSharedPreferences()
            saveQuestionsListToSharedPreferences(Questions)

            // Update flashcardsTextView with the saved text
            flashcardsTextView.text = retrieveSavedText()
            Questions=retrieveQuestions()

            // Clear input fields
            questionInput.text.clear()
            answerInput.text.clear()

            Log.d("MYLIST",Questions.toString())
        }
    }

    private fun saveQuestionsToSharedPreferences() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the MutableList to a JSON string
        val questionsJson = Gson().toJson(Questions)

        // Save the JSON string in SharedPreferences
        editor.putString("questionsList", questionsJson)
        editor.apply()
    }

    private fun retrieveSavedText(): String {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val questionsJson = sharedPreferences.getString("questionsList", "")

        // Convert the JSON string back to a MutableList
        val savedQuestions: MutableList<QuizItem> =
            Gson().fromJson(questionsJson, object : TypeToken<MutableList<QuizItem>>() {}.type)
                ?: mutableListOf()

        // Create a string representation of the saved questions
        return savedQuestions.joinToString("\n") { "Q:${it.question} A:${it.answer}" }
    }

    private fun retrieveQuestions(): MutableList<QuizItem> {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val questionsJson = sharedPreferences.getString("questionsList", "")

        // Convert the JSON string back to a MutableList
        return Gson().fromJson(questionsJson, object : TypeToken<MutableList<QuizItem>>() {}.type)
            ?: mutableListOf()
    }

    private fun saveQuestionsListToSharedPreferences(questions: MutableList<QuizItem>) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Save the MutableList<QuizItem> directly in SharedPreferences
        val jsonAdapter = Gson().getAdapter(object : TypeToken<MutableList<QuizItem>>() {})
        val questionsJson = jsonAdapter.toJson(questions)

        editor.putString("questionsList", questionsJson)
        editor.apply()
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






}