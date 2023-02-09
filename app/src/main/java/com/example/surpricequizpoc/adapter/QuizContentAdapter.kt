package com.example.surpricequizpoc.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.model.Questions

class QuizContentAdapter(
    private val questionList : MutableList<Questions>,
    private val addNewOption : (Int)-> Unit,
    private val deleteOption : (Int,Int)-> Unit,
    private val deleteQuestion : (Int)-> Unit,
    private val questionTitleChange : (String,Int)-> Unit,
    private val optionTitleChange : (String,Int,Int) -> Unit,
    private val addAnotherQuestion : () -> Unit,
    private val copyQuestion : (Int, Questions)-> Unit
):RecyclerView.Adapter<QuizContentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
           .inflate(R.layout.question_create_custom_item,parent,false)
        return ViewHolder(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionPosition = questionList[position]

        with(holder){
            uiEtQuestionName.hint = "${position+1} Question Name : "
            uiEtQuestionName.setText(questionPosition.questionTitle)
            uiRvOptions.adapter = OptionContentAdapter(
                optionList = questionPosition.options,
                deleteOptionItem =  {optionPosition ->
                    deleteOption(position,optionPosition)
                },
                onOptionTitleChange = {optionText,optionPosition->
                    optionTitleChange(optionText,position,optionPosition)
                }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
       // val uiTiQuestionTitle:TextView = itemView.findViewById(R.id.uiTiQuestionTitle)
        val uiEtQuestionName:EditText = itemView.findViewById(R.id.uiEtQuizName)
        val uiRvOptions:RecyclerView = itemView.findViewById(R.id.uiRvOptions)
        private val uiBtAddOption:Button = itemView.findViewById(R.id.uiBtAddOption)
        val uiTvSetAnswerKey:TextView = itemView.findViewById(R.id.uiTvSetAnswerKey)
        private val uiIvAdd:ImageView = itemView.findViewById(R.id.uiIvAdd)
        private val uiIvCopy:ImageView = itemView.findViewById(R.id.uiIvCopy)
        private val uiIvDelete:ImageView = itemView.findViewById(R.id.uiIvDelete)

        init{
            uiBtAddOption.setOnClickListener {
                addNewOption(adapterPosition)
                notifyDataSetChanged()
            }

            uiEtQuestionName.doAfterTextChanged {
                questionTitleChange(it.toString(),adapterPosition)
            }

            uiIvAdd.setOnClickListener {
                addAnotherQuestion()
            }

            uiIvCopy.setOnClickListener {
                copyQuestion(adapterPosition,questionList[adapterPosition])
            }

            uiIvDelete.setOnClickListener {
                deleteQuestion(adapterPosition)
                notifyDataSetChanged()
            }

        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onQuestionListChanged(question: MutableList<Questions>) {
        questionList.clear()
        questionList.addAll(question)
        notifyDataSetChanged()
    }
}