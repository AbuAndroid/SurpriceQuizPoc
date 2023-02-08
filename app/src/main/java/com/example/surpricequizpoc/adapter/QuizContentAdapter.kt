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
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.model.Questions

class QuizContentAdapter(
    private val questionList : MutableList<Questions>,
    private val addOption : (Int)-> Unit,
    private val deleteOption : (Int,Int)-> Unit,
    private val deleteQuestion : (Int)-> Unit,
    private val questionTitleChange : (String,Int)-> Unit
):RecyclerView.Adapter<QuizContentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
           .inflate(R.layout.question_create_custom_item,parent,false)
        return ViewHolder(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionPosition = questionList[position]
       Log.e("fl",questionList.toString())
        with(holder){
            uiRvOptions.adapter = OptionContentAdapter(
                optionList = questionPosition.options,
                deleteOptionItem =  {optionPosition ->
                    deleteOption(position,optionPosition)
                },
                onQuestionTitleChange = questionTitleChange
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val uiEtQuestionName:EditText = itemView.findViewById(R.id.uiEtQuestionName)
        val uiRvOptions:RecyclerView = itemView.findViewById(R.id.uiRvOptions)
        private val uiBtAddOption:Button = itemView.findViewById(R.id.uiBtAddOption)
        val uiTvSetAnswerKey:TextView = itemView.findViewById(R.id.uiTvSetAnswerKey)
        private val uiIvAdd:ImageView = itemView.findViewById(R.id.uiIvAdd)
        private val uiIvCopy:ImageView = itemView.findViewById(R.id.uiIvCopy)
        private val uiIvDelete:ImageView = itemView.findViewById(R.id.uiIvDelete)

        init{
            uiBtAddOption.setOnClickListener {
                addOption(adapterPosition)
                notifyDataSetChanged()
            }

            uiEtQuestionName.doAfterTextChanged {
                questionList[adapterPosition].questionTitle = it.toString()
                questionTitleChange(it.toString(),adapterPosition)
                Log.e("text",questionList[adapterPosition].toString())
            }

            uiIvAdd.setOnClickListener {

            }

            uiIvCopy.setOnClickListener {

            }

            uiIvDelete.setOnClickListener {
                Log.e("pos",adapterPosition.toString())
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