package com.example.surpricequizpoc.model

data class Quiz(
    val quizTitle:String?,
    val questions:ArrayList<Questions>
)

data class Questions(
    var questionTitle:String?,
    val options: MutableList<Options>
)

data class Options(
    var option : String?,
    var isAnswer : Boolean = false,
)
