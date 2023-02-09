package com.example.surpricequizpoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.adapter.OptionListBottomList
import com.example.surpricequizpoc.adapter.QuizContentAdapter
import com.example.surpricequizpoc.databinding.ActivityMainBinding
import com.example.surpricequizpoc.model.Questions
import com.google.android.material.bottomsheet.BottomSheetDialog


import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val quizViewModel: QuizViewModel by viewModel()

    private var quizContentAdapter: QuizContentAdapter? = null

    private var optionListBottomList: OptionListBottomList? = null

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
            copyQuestion = ::copyQuestion,
            onOptionSelected = ::onOptionSelected,
            setAnswerKey = ::setAnswerKey
        )
        binding.uiRvQuestion.adapter = quizContentAdapter
        (binding.uiRvQuestion.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
        quizViewModel.copyQuestion(questionCardPosition, questions)
    }

    private fun onOptionSelected(questionPosition: Int, optionPosition: Int) {
        quizViewModel.onOptionSelected(questionPosition, optionPosition)
    }

    private fun setAnswerKey(questionPosition: Int) {
        val question = quizViewModel.quizDataList.value
        question?.get(questionPosition)?.let { Log.e("listData", it.options.toString()) }

        optionListBottomList = OptionListBottomList(
            optionList = question?.get(questionPosition)?.options ?: mutableListOf(),
            onOptionSelected = { option ->
                onOoptionSelectedFromBottomList(questionPosition, option)
            }
        )
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.options_select_bottomsheet, null)
        val btnClose = view.findViewById<ImageView>(R.id.uiIvBottomCloseClose)
        val questionTitle = view.findViewById<TextView>(R.id.uiTvBtmQuestionName)
        questionTitle.text ="${questionPosition+1}, "+ question?.get(questionPosition)?.questionTitle ?: ""
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        val optionsRv = view.findViewById<RecyclerView>(R.id.uiRvBtmOptionSelect)
        optionsRv.adapter = optionListBottomList
        dialog.show()
    }

    private fun onOoptionSelectedFromBottomList(questionPosition: Int, optionPosition: Int) {
        quizViewModel.onOptionSelected(questionPosition, optionPosition)
    }


}