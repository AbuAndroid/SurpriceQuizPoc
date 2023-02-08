package com.example.surpricequizpoc.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.model.Options


class OptionContentAdapter(
    private val optionList : MutableList<Options>,
    private val deleteOptionItem : (Int)->Unit,
    private val onOptionTitleChange: (String,Int) -> Unit
): RecyclerView.Adapter<OptionContentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.options_create_custom_item,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val layoutposition = optionList[position]
        with(holder){
            uiRbOptions.isChecked = layoutposition.isAnswer
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val uiRbOptions:RadioButton = itemView.findViewById(R.id.uiRbOptions)
        val uiEtoption:EditText = itemView.findViewById(R.id.uiEtoption)
        val uiIvClearQuestion:ImageView = itemView.findViewById(R.id.uiIvClearQuestion)

        init {
            uiIvClearQuestion.setOnClickListener {
                deleteOptionItem(adapterPosition)
                notifyDataSetChanged()
            }

            uiEtoption.doAfterTextChanged {
                optionList[adapterPosition].option = it.toString()
                onOptionTitleChange(it.toString(),adapterPosition)
                Log.e("op",optionList[adapterPosition].toString())
            }
        }

    }


    override fun getItemCount(): Int {
        return optionList.size
    }

}