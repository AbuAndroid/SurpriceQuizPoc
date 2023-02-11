package com.example.surpricequizpoc.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.model.Questions
import com.google.android.material.textfield.TextInputLayout

class QuestionAdapter(
    private val questionList: MutableList<Questions>,
    private val addNewOption: (Int) -> Unit,
    private val deleteOption: (Int, Int) -> Unit,
    private val deleteQuestion: (Int) -> Unit,
    private val questionTitleChange: (String, Int) -> Unit,
    private val optionTitleChange: (String, Int, Int) -> Unit,
    private val addAnotherQuestion: () -> Unit,
    private val copyQuestion: (Int, Questions) -> Unit,
    private val onOptionSelected: (Int, String) -> Unit,
    private val setAnswerKey: (Int)-> Unit,
    private val getQuestionImage: (Int)->Unit,
    private val getOptionImage:(Int,Int)->Unit
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_create_custom_item, parent, false)
        return ViewHolder(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionPosition = questionList[position]

        with(holder) {
            uiTiQuestionLabel.hint = "${position + 1} Question Name : "
            uiEtQuestionName.setText(questionPosition.questionTitle)
            uiIvQuestionImage.setImageURI(questionPosition.questionImage?.toUri())
            uiRvOptions.adapter = OptionsAdapter(
                optionList = questionPosition.options,
                deleteOptionItem = { optionPosition ->
                    deleteOption(position, optionPosition)
                    notifyDataSetChanged()
                },
                onOptionTitleChange = { optionText, optionPosition ->
                    optionTitleChange(optionText, position, optionPosition)
                },
                getOptionImage = {optionPosition->
                    getOptionImage(adapterPosition,optionPosition)
                }
            )

            uiEtQuestionName.doAfterTextChanged {
                questionTitleChange(it.toString(), position)
            }

            uiTvSetAnswerKey.setOnClickListener {
                setAnswerKey(position)
            }
            if(questionList[adapterPosition].questionImage?.isNotEmpty() == true)
                uiIvQuestionImage.visibility = View.VISIBLE
            else
                uiIvQuestionImage.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            Log.e("img",questionPosition.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val uiIvPickImage:ImageView = itemView.findViewById(R.id.uiIvPickImage)
        val uiIvQuestionImage:ImageView = itemView.findViewById(R.id.uiIvQuestionImage)
        val uiTiQuestionLabel :TextInputLayout = itemView.findViewById(R.id.uiTiQuestionTitle)
        val uiEtQuestionName: EditText = itemView.findViewById(R.id.uiEtQuizName)
        val uiRvOptions: RecyclerView = itemView.findViewById(R.id.uiRvOptions)
        val uiTvSetAnswerKey: TextView = itemView.findViewById(R.id.uiTvSetAnswerKey)
        private val uiBtAddOption: Button = itemView.findViewById(R.id.uiBtAddOption)
        private val uiIvAdd: ImageView = itemView.findViewById(R.id.uiIvAdd)
        private val uiIvCopy: ImageView = itemView.findViewById(R.id.uiIvCopy)
        private val uiIvDelete: ImageView = itemView.findViewById(R.id.uiIvDelete)

        init {


            uiBtAddOption.setOnClickListener {
                addNewOption(adapterPosition)
                notifyDataSetChanged()
            }

            uiIvAdd.setOnClickListener {
                addAnotherQuestion()
                notifyDataSetChanged()
            }

            uiIvCopy.setOnClickListener {
                copyQuestion(adapterPosition, questionList[adapterPosition])
                notifyDataSetChanged()
            }

            uiIvDelete.setOnClickListener {
                deleteQuestion(adapterPosition)
                notifyDataSetChanged()
            }

            uiIvPickImage.setOnClickListener {
                getQuestionImage(adapterPosition)
                //notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}