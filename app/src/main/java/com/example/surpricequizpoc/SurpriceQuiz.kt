package com.example.surpricequizpoc

import android.app.Application
import com.example.surpricequizpoc.di.module.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SurpriceQuiz:Application() {

    override fun onCreate() {
        super.onCreate()
        configKoin()
    }

    private fun configKoin() {
        startKoin{
            androidContext(this@SurpriceQuiz)
            modules(Test.module())
        }
    }
}