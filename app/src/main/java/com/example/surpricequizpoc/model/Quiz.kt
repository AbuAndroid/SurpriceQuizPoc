package com.example.surpricequizpoc.model

data class Quiz(
    val quizTitle:String?,
    val questions:ArrayList<Questions>
)

data class Questions(
    var questionId:String?,
    var questionTitle:String?,
    val options: MutableList<Options>
)

data class Options(
    var optionId:String?,
    var option : String?,
    var isAnswer : Boolean = false,
)
