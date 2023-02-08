package com.example.surpricequizpoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.surpricequizpoc.adapter.QuizContentAdapter
import com.example.surpricequizpoc.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val quizViewModel:QuizViewModel by viewModel()

    private val quizContentAdapter : QuizContentAdapter by lazy {
        QuizContentAdapter(
            questionList = mutableListOf(),
            addOption = ::addOption,
            deleteOption = ::removeOption,
            deleteQuestion = ::removeQuestion,
            questionTitleChange = ::onQuestionTitleChange
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpUi()
        setUpListener()
        setQuestionsToUi()
    }

    private fun setUpUi() {
        binding.uiRvQuestion.adapter = quizContentAdapter
    }

    private fun setUpListener() {
        binding.uiBtAddQuestions.setOnClickListener {
            quizViewModel.addQuestion()
            quizContentAdapter.onQuestionListChanged(quizViewModel.quizDataList.value?.toMutableList() ?: mutableListOf())
        }
    }

    private fun setQuestionsToUi() {
        Log.e("data",quizViewModel.quizDataList.value.toString())
        quizContentAdapter.onQuestionListChanged(quizViewModel.quizDataList.value?.toMutableList() ?: mutableListOf())
    }

    private fun addOption(questionPosition: Int) {
        quizViewModel.addOption(questionPosition)
    }

    private fun removeOption(questionPosition: Int, optionPosition: Int) {
        quizViewModel.removeOption(questionPosition,optionPosition)

    }

    private fun removeQuestion(questionPosition : Int) {
        quizViewModel.removeQuestion(questionPosition)
        quizContentAdapter.onQuestionListChanged(quizViewModel.quizDataList.value?.toMutableList() ?: mutableListOf())
    }

    private fun onQuestionTitleChange(questionTile: String,questionPosition: Int) {
        quizViewModel.onQuestionTitleChange(questionTile,questionPosition)
    }

}