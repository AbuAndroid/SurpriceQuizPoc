package com.example.surpricequizpoc.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.adapter.setAnswerKeyAdapter
import com.example.surpricequizpoc.adapter.QuestionAdapter
import com.example.surpricequizpoc.databinding.ActivityMainBinding
import com.example.surpricequizpoc.model.Questions
import com.google.android.material.bottomsheet.BottomSheetDialog


import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val quizViewModel: QuizViewModel by viewModel()

    private var questionAdapter: QuestionAdapter? = null

    private var setAnswerKeyAdapter: setAnswerKeyAdapter? = null

    var questionImage:Uri?=null
    private val resultPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.GetContent()){
            questionImage=it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        resultPermissionLauncher
        setUpObserver()
        setUpListener()
    }

    private fun setUpObserver() {
        quizViewModel.quizDataList.observe(this) { questionList ->
            setUpAdapter(questionList)
        }
    }

    private fun setUpAdapter(questionList: List<Questions>?) {
        questionAdapter = QuestionAdapter(
            questionList = questionList?.toMutableList() ?: mutableListOf(),
            addNewOption = ::addNewOption,
            deleteOption = ::removeOption,
            deleteQuestion = ::removeQuestion,
            questionTitleChange = ::onQuestionTitleChange,
            optionTitleChange = ::onOptionTextChange,
            addAnotherQuestion = ::addQuestion,
            copyQuestion = ::copyQuestion,
            onOptionSelected = ::onOptionSelected,
            setAnswerKey = ::setAnswerKey,
            getQuestionImage = ::getQuestionImage
        )


        binding.uiRvQuestion.adapter = questionAdapter
        (binding.uiRvQuestion.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun setUpListener() {
        binding.uiBtAddQuestions.setOnClickListener {
            Log.e("size", quizViewModel.quizDataList.value?.size.toString())
            val listSize = quizViewModel.quizDataList.value?.size
            if (listSize != null) {
                if(listSize <= 3)
                    quizViewModel.addQuiz()
                else
                    Toast.makeText(this,"You are only allowed to Create Only 4 Questions ",Toast.LENGTH_SHORT).show()
            }
        }

        binding.uiBtCancel.setOnClickListener {
             resultPermissionLauncher.launch("image/*")
        }

    }

    private fun addQuestion() {
        if(quizViewModel.quizDataList.value!!.size <= 3){
            quizViewModel.addQuiz()
        }else{
            Toast.makeText(this,"maximum Four Questions allowed ",Toast.LENGTH_SHORT).show()
        }

    }

    private fun addNewOption(questionPosition: Int) {
        val optionSize = quizViewModel.quizDataList.value?.get(questionPosition)?.options?.size
        if (optionSize != null) {
            if(optionSize <= 3)
                quizViewModel.addOption(questionPosition)
            else
                Toast.makeText(this,"don't create more than 4 options",Toast.LENGTH_SHORT).show()
        }
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
        val listSize = quizViewModel.quizDataList.value?.size
        if (listSize != null) {
            if(listSize <= 3)
                quizViewModel.copyQuestion(questionCardPosition, questions)
            else
                Toast.makeText(this,"You are only allowed to Create Only 4 Questions ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun onOptionSelected(questionPosition: Int, optionPosition: String) {
        quizViewModel.onOptionSelected(questionPosition, optionPosition)
    }

    private fun getQuestionImage(questionPostion: Int) {
        resultPermissionLauncher.launch("image/*")
        questionImage?.let { quizViewModel.addQuestionImage(questionPostion, questionImage = it) }
        Toast.makeText(this@QuizActivity,questionPostion.toString(),Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("InflateParams")
    private fun setAnswerKey(questionPosition: Int) {
        val question = quizViewModel.quizDataList.value

        val questionName = question?.get(questionPosition)?.questionTitle
        if(questionName?.isNotEmpty() == true){
            val view = layoutInflater.inflate(R.layout.options_select_bottomsheet, null)
            val optionsRv = view.findViewById<RecyclerView>(R.id.uiRvBtmOptionSelect)
            val dialog = BottomSheetDialog(this)
            val btnClose = view.findViewById<ImageView>(R.id.uiIvBottomCloseClose)
            val questionTitle = view.findViewById<TextView>(R.id.uiTvBtmQuestionName)

            if(question[questionPosition].options.size >= 3){

                var optionEmpty = false
                question[questionPosition].options.forEach { options->
                    if(options.option?.isEmpty() == true)
                        optionEmpty=true
                }
                if(optionEmpty){
                    Toast.makeText(this,"Please Enter Options..",Toast.LENGTH_SHORT).show()
                }else{
                    setAnswerKeyAdapter = setAnswerKeyAdapter(
                        optionList = question[questionPosition].options,
                        onAnswerKeySelected = { option ->
                            onOptionSelectedFromBottomList(questionPosition, option)
                            dialog.dismiss()
                        }
                    )
                    optionsRv.adapter = setAnswerKeyAdapter
                    questionTitle.text =
                        ("${questionPosition + 1}, " + question[questionPosition].questionTitle)
                    btnClose.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.setCancelable(false)
                    dialog.setContentView(view)
                    dialog.show()
                }

            }else{
                Toast.makeText(this,"Minimum 3 Option Required..",Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this@QuizActivity,"Question Name Required",Toast.LENGTH_LONG).show()
        }
    }

    private fun onOptionSelectedFromBottomList(questionPosition: Int, optionId: String) {
        quizViewModel.onOptionSelected(questionPosition, optionId)
    }

}