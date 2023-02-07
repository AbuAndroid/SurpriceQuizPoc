package com.example.surpricequizpoc.di.module


import com.example.surpricequizpoc.repository.QuizRepository
import com.example.surpricequizpoc.ui.QuizViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Test {
    fun module() = repoModule + viewModelModule
}

val repoModule = module {
    single {
        QuizRepository()
    }
}

val viewModelModule = module {
    viewModel{
        QuizViewModel(get())
    }
}