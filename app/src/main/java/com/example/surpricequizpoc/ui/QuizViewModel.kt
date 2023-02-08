package com.example.surpricequizpoc.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surpricequizpoc.model.Options
import com.example.surpricequizpoc.model.Questions
import com.example.surpricequizpoc.repository.QuizRepository

class QuizViewModel(private val quizRepository: QuizRepository):ViewModel() {

    private var quizDataListLd = MutableLiveData<List<Questions>>()

    val quizDataList:LiveData<List<Questions>> = quizDataListLd

     private val questionList = mutableListOf<Questions>()

    init{
        getQuiz()
    }

    private fun getQuiz() {
        quizDataListLd.value = questionList
    }

    fun addQuestion() {
        questionList.add(
            Questions(
                questionTitle = "",
                options = mutableListOf(
                    Options(
                        option = "",
                        isAnswer = false
                    )
                )
            )
        )
        quizDataListLd.value = questionList
    }

    fun addOption(questionPosition: Int) {
        questionList.map { question->
            if(questionList[questionPosition] == question){
                question.options.add(
                    Options(
                        option = "",
                        isAnswer = false
                    )
                )
            }
        }
    }

    fun removeOption(questionPosition: Int, optionPosition: Int) {
        questionList.map { question->
           if(questionList[questionPosition] == question){
                question.options.removeAt(optionPosition)
           }
        }
    }

    fun removeQuestion(questionPosition: Int) {
        questionList.removeAt(questionPosition)
    }

    fun onQuestionTitleChange(questionTile: String,questionPosition: Int) {
        questionList.map { question->
            if(questionList[questionPosition] == question){
                questionList[questionPosition].questionTitle = questionTile
            }
        }
    }


}