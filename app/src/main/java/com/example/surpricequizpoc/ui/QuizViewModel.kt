package com.example.surpricequizpoc.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surpricequizpoc.model.Options
import com.example.surpricequizpoc.model.Questions

class QuizViewModel() : ViewModel() {

    private var quizDataListLd = MutableLiveData<List<Questions>>()

    val quizDataList: LiveData<List<Questions>> = quizDataListLd

    private var questionList = mutableListOf<Questions>()

    init {
        getQuiz()
    }

    fun getQuiz() {
        val question = Questions(
            questionTitle = "",
            options = mutableListOf(
                Options(
                    option = "",
                    isAnswer = false
                )
            )
        )
        questionList.add(question)
        quizDataListLd.value = questionList
    }

    fun addOption(questionPosition: Int) {
        val question = questionList[questionPosition]
        val option = Options(option = "",isAnswer = false)
        question.options.add(option)
        quizDataListLd.value = questionList
    }

    fun removeOption(questionPosition: Int, optionPosition: Int) {
        questionList[questionPosition].options.removeAt(optionPosition)
        quizDataListLd.value = questionList
    }

    fun removeQuestion(questionPosition: Int) {
        questionList.removeAt(questionPosition)
        quizDataListLd.value = questionList
    }

    fun onQuestionTitleChange(questionTile: String, questionPosition: Int) {
        val question = questionList[questionPosition]
        question.questionTitle = questionTile

    }

    fun onOptionTextChange(optionText: String, questionPosition: Int, optionPosition: Int) {
        val question = questionList[questionPosition]
        val option = question.options[optionPosition]
        option.option = optionText
    }

    fun copyQuestion(questionCardPosition: Int, questions: Questions) {
        val optionList = mutableListOf<Options>()
        questions.options.forEach { options ->
            optionList.add(options)
        }

        val questionCard = Questions(
            questionTitle = questions.questionTitle,
            options = optionList
        )
        questionList.add(questionCardPosition+1,questionCard)
        quizDataListLd.value = questionList
    }


}