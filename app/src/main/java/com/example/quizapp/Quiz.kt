package com.example.quizapp

class Quiz (var questions: List<Question>){


    //variable to track score, current question
    var score = 0
    var questionNumber = 0
    var currentQuestion = questions.get(0)
    //functions to check if there's another question,
    fun nextQuestion(): Boolean{
        if(questionNumber+1 < questions.size){
            questionNumber++
            currentQuestion = questions.get(questionNumber)
            return true
        }
        return false
    }
    //give the next question, check the answer
    fun checkQuestion(choice: Boolean){
        currentQuestion = questions.get(questionNumber)
        if(currentQuestion.answer == choice){
            score++
        }
        else{
            return
        }
    }
    //give the final score, reset the quiz?, shuffle questions?
    fun reset(){
        score = 0
        questionNumber = 0
        currentQuestion = questions.get(0)
    }
    fun shuffle(){
        questions = questions.shuffled()
        reset()
    }
}
