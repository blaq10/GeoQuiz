package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate (Bundle?) called")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener{view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener{view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {view: View ->
            nextQuestion()
        }

        previousButton.setOnClickListener {view: View ->
            previousQuestion()
        }

        questionTextView.setOnClickListener {view: View ->
            nextQuestion()
        }

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        var questionID = quizViewModel.currentQuestionText
        questionTextView.setText(questionID)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(KEY_INDEX, "onSaveInstanceState")

        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun nextQuestion()
    {
        quizViewModel.moveToNext()
        questionTextView.setText(quizViewModel.currentQuestionText)
    }

    private fun previousQuestion()
    {
        if (quizViewModel.currentIndex != 0)
        {
            quizViewModel.currentIndex = (quizViewModel.currentIndex - 1)
            questionTextView.setText(quizViewModel.currentQuestionText)
        }
        else
        {
            Toast.makeText(this, "Answer at least one question", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAnswer(userAnswer:Boolean)
    {
        var toastMessage: String
        if (userAnswer == quizViewModel.currentQuestionAnswer)
        {
            toastMessage = "Correct"
        }
        else
        {
            toastMessage = "Incorrect"
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}
