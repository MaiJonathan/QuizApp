package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {




    companion object {
        // all your static constants go here
        val TAG = "MainActivity"
    }

    private lateinit var quiz: Quiz
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var restartButton: Button
    private lateinit var shuffleButton: Button
    private lateinit var displayedQuestion: TextView
    private lateinit var displayedScore: TextView
    private lateinit var finalScore: TextView
    private lateinit var groupFinished: Group
    private lateinit var groupUnfinished: Group
    private lateinit var scoreText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wireWidgets()
        loadQuestions()
        //thing
        scoreText = getString(R.string.main_score)
        //get the first question, set up the textviews
        displayQuestion()
        groupUnfinished.isVisible = true
        groupFinished.isVisible = false
        displayedQuestion.setTextSize(25f)
        //set up the onclick listeners.
        trueButton.setOnClickListener {
            quiz.checkQuestion(true)
            if(quiz.nextQuestion()) {
                displayQuestion()
            }
            else{
                quizCompleted()
            }
        }
        falseButton.setOnClickListener {
            quiz.checkQuestion(false)
            if(quiz.nextQuestion()) {
                displayQuestion()

            }
            else{
                quizCompleted()
            }
        }
        restartButton.setOnClickListener {
            quiz.reset()
            quizUncompleted()
        }
        shuffleButton.setOnClickListener {
           quiz.shuffle()
            quizUncompleted()
        }
    }

    private fun quizCompleted() {
        groupUnfinished.isVisible = false
        groupFinished.isVisible = true
        finalScore.setTextSize(50F)
        val quack = getString(R.string.main_quizCompleted)
        val flop = getString(R.string.main_finaScoreText)
        finalScore.text = "${quack}" +
                "\n" +
                "${flop} ${quiz.score} / ${quiz.questionNumber+1}"
    }

    private fun quizUncompleted()
    {
        groupUnfinished.isVisible = true
        groupFinished.isVisible = false
        displayQuestion()
    }

    private fun wireWidgets() {
        trueButton = findViewById(R.id.button_main_true)
        falseButton = findViewById(R.id.button_main_false)
        displayedQuestion = findViewById(R.id.textView_main_question)
        displayedScore = findViewById(R.id.textView_main_score)
        finalScore = findViewById(R.id.textView_main_finalScore)
        groupUnfinished = findViewById(R.id.group_main_unfinishedQuiz)
        groupFinished = findViewById(R.id.group_main_completedQuiz)
        restartButton = findViewById(R.id.button_main_restart)
        shuffleButton = findViewById(R.id.button_main_shuffle)
    }
    private fun displayQuestion(){
        displayedQuestion.text = quiz.currentQuestion.question
        displayedScore.text = "${scoreText}" + quiz.score
    }

    private fun loadQuestions() {
        // reading the json from the raw folder

        // step 1: open the raw resource as an InputStream
        // R.raw.questions --> R is the Resources class, raw is folder,
        // questions is the file
        val inputStream = resources.openRawResource(R.raw.questions)
        // step 2: use a buffered reader on the inputstream to read the
        // the text into a string. we call it jsonString because it's the entire JSON file in a string
        val jsonString = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }
        Log.d(TAG, "onCreate: $jsonString")



        // use gson to convert the jsonText into a List<Question>
        // https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
        // check the section called "Parsing between a Collection, List, or Array"
        // log the list of questions and see if your Question objects all appear correct
        val gson = Gson()
        // gson needs to know what kind of list you are converting to
        // typetoken tells gson it will be a List<Question>

        val type = object : TypeToken<List<Question>>() { }.type
        val questions = gson.fromJson<List<Question>>(jsonString, type)

        Log.d(TAG, "onCreate: $questions")
        //quiz app
        quiz = Quiz(questions)

    }


}