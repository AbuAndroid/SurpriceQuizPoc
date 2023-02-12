package com.example.surpricequizpoc.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surpricequizpoc.model.Options
import com.example.surpricequizpoc.model.Questions

class QuizViewModel : ViewModel() {

    private var quizDataListLd = MutableLiveData<List<Questions>>()

    val quizDataList: LiveData<List<Questions>> = quizDataListLd

    private var questionList = mutableListOf<Questions>()

    init {
        addQuiz()
    }

    fun addQuiz() {
        val question = Questions(
            questionId = System.currentTimeMillis().toString(),
            questionTitle = "",
            questionImage = "",
            options = mutableListOf(
                Options(
                    optionId = System.currentTimeMillis().toString(),
                    option = "",
                    optionImage = "",
                    isAnswer = false
                )
            )
        )
        questionList.add(question)
        quizDataListLd.value = questionList
    }

    fun addOption(questionPosition: Int) {
        val question = questionList[questionPosition]
        val option = Options(optionId = System.currentTimeMillis().toString(),option = "",  optionImage = "",isAnswer = false)
        question.options.add(option)
        quizDataListLd.value = questionList
    }

    fun addQuestionImage(questionPosition: Int, questionImage: Uri?) {
        val question = questionList[questionPosition]
        question.questionImage = questionImage.toString()
        quizDataListLd.value = questionList
    }

    fun addOptionImage(questionPosition: Int, optionPosition: Int, optionImage: Uri?) {
        val question = questionList[questionPosition]
        val option = question.options[optionPosition]
        option.optionImage = optionImage.toString()
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
        val optionList = questions.options.mapIndexed{index, option->
            Options(
                optionId = System.currentTimeMillis().toString()+index,
                option = option.option,
                optionImage = option.optionImage,
                isAnswer = option.isAnswer
            )
        }
        val questionCard = Questions(
            questionId = System.currentTimeMillis().toString(),
            questionTitle = questions.questionTitle,
            questionImage = questions.questionImage,
            options = optionList.toMutableList()
        )
        questionList.add(questionCardPosition+1,questionCard)
        quizDataListLd.value = questionList
    }

    fun onOptionSelected(questions: Int, optionId: String) {
        val question = questionList[questions]

        question.options.forEach {
            it.isAnswer = false
            if(it.optionId == optionId){
                it.isAnswer=true
            }
        }
        quizDataListLd.value = questionList
    }

}