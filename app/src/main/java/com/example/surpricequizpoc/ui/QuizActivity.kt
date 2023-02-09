package com.example.surpricequizpoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.surpricequizpoc.adapter.QuizContentAdapter
import com.example.surpricequizpoc.databinding.ActivityMainBinding
import com.example.surpricequizpoc.model.Questions


import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val quizViewModel: QuizViewModel by viewModel()

    private var quizContentAdapter: QuizContentAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpObserver()
        setUpListener()

    }

    private fun setUpObserver() {
        quizViewModel.quizDataList.observe(this) { questionList ->
            setUpAdapter(questionList)
        }
    }

    private fun setUpAdapter(questionList: List<Questions>?) {
        quizContentAdapter = QuizContentAdapter(
            questionList = questionList?.toMutableList() ?: mutableListOf(),
            addNewOption = ::addNewOption,
            deleteOption = ::removeOption,
            deleteQuestion = ::removeQuestion,
            questionTitleChange = ::onQuestionTitleChange,
            optionTitleChange = ::onOptionTextChange,
            addAnotherQuestion = ::addAnotherOption,
            copyQuestion = ::copyQuestion
        )
        binding.uiRvQuestion.adapter = quizContentAdapter
    }

    private fun setUpListener() {
        binding.uiBtAddQuestions.setOnClickListener {
            quizViewModel.getQuiz()
        }
    }

    private fun addAnotherOption() {
        quizViewModel.getQuiz()
    }

    private fun addNewOption(questionPosition: Int) {
        quizViewModel.addOption(questionPosition)
    }

    private fun removeOption(questionPosition: Int, optionPosition: Int) {
        quizViewModel.removeOption(questionPosition, optionPosition)
    }

    private fun removeQuestion(questionPosition: Int) {
        quizViewModel.removeQuestion(questionPosition)
    }

    private fun onQuestionTitleChange(questionTile: String, questionPosition: Int) {
        quizViewModel.onQuestionTitleChange(questionTile, questionPosition)
    }

    private fun onOptionTextChange(optionText: String, questionPosition: Int, optionPosition: Int) {
        quizViewModel.onOptionTextChange(optionText, questionPosition, optionPosition)
    }

    private fun copyQuestion(questionCardPosition: Int, questions: Questions) {
        quizViewModel.copyQuestion(questionCardPosition,questions)
    }
}