package com.example.surpricequizpoc.adapter

import android.annotation.SuppressLint
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


class OptionsAdapter(
    private val optionList : MutableList<Options>,
    private val deleteOptionItem : (Int)->Unit,
    private val onOptionTitleChange: (String,Int) -> Unit,
   // private val onOptionSelected:(String)-> Unit
): RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.options_create_custom_item,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val layoutposition = optionList[position]
        with(holder){
            uiEtoption.hint = "option ${position+1} : "
            uiEtoption.setText(layoutposition.option)
            uiRbOptions.isChecked = layoutposition.isAnswer
            uiRbOptions.isEnabled=false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val uiRbOptions:RadioButton = itemView.findViewById(R.id.uiRbOptions)
        val uiEtoption:EditText = itemView.findViewById(R.id.uiEtoption)
        val uiIvDeleteOption:ImageView = itemView.findViewById(R.id.uiIvClearQuestion)

        init {
            uiIvDeleteOption.setOnClickListener {
                deleteOptionItem(adapterPosition)
                notifyDataSetChanged()
            }

            uiEtoption.doAfterTextChanged {
                onOptionTitleChange(it.toString(),adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

}