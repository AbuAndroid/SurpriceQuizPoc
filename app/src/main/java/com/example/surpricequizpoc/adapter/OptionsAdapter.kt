package com.example.surpricequizpoc.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.surpricequizpoc.R
import com.example.surpricequizpoc.model.Options


class OptionsAdapter(
    private val optionList : MutableList<Options>,
    private val deleteOptionItem : (Int)->Unit,
    private val onOptionTitleChange: (String,Int) -> Unit,
    private val getOptionImage:(Int)->Unit
): RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.options_create_custom_item,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val optionPosition = optionList[position]
        with(holder){
            uiEtOption.hint = "option ${position+1} : "
            uiEtOption.setText(optionPosition.option)
            uiRbOptions.isChecked = optionPosition.isAnswer
            uiRbOptions.isEnabled=false
            uiIvOptionImage.setImageURI(optionPosition.optionImage?.toUri())
//            if(optionPosition.option?.isNotEmpty()==true)
//                uiIvOptionImage.visibility=View.VISIBLE
//            else
//                uiIvOptionImage.visibility=View.GONE

            if(optionList[adapterPosition].optionImage?.isNotEmpty()==true){
                uiIvOptionImage.visibility=View.VISIBLE
            }else{
                uiIvOptionImage.visibility=View.GONE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val uiRbOptions:RadioButton = itemView.findViewById(R.id.uiRbOptions)
        val uiEtOption:EditText = itemView.findViewById(R.id.uiEtoption)
        private val uiIvOptionSelectImage:ImageView = itemView.findViewById(R.id.uiIvOptionSelectImage)
        private val uiIvDeleteOption:ImageView = itemView.findViewById(R.id.uiIvClearQuestion)
        val uiIvOptionImage:ImageView = itemView.findViewById(R.id.uiIvOptionImage)
        init {
            uiIvDeleteOption.setOnClickListener {
                deleteOptionItem(adapterPosition)
                notifyDataSetChanged()
            }
            uiIvOptionSelectImage.setOnClickListener {
                getOptionImage(adapterPosition)
                notifyDataSetChanged()
            }
            uiEtOption.doAfterTextChanged {
                onOptionTitleChange(it.toString(),adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

}