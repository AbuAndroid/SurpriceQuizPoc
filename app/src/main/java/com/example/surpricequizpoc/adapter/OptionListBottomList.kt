package com.example.surpricequizpoc.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.model.Options

class OptionListBottomList(
    private val optionList : MutableList<Options>,
    private val onOptionSelected : (Int)->Unit
): RecyclerView.Adapter<OptionListBottomList.ViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val uiRbSelectOption = itemview.findViewById<RadioButton>(R.id.uiRbSelectOption)

        init {
            uiRbSelectOption.setOnClickListener {
                onOptionSelected(adapterPosition)
                notifyDataSetChanged()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.option_select_bottomsheet_item,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val optionPosition = optionList[position]
        holder.uiRbSelectOption.text = optionPosition.option
        holder.uiRbSelectOption.isChecked = optionPosition.isAnswer
    }

    override fun getItemCount(): Int {
        return optionList.size
    }
}