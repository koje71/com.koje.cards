package com.koje.framework

import android.app.Application
import android.content.Context
import com.koje.cards.R


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        init(this)

    }

    companion object {
        val idReceivers = R.id.receivers
        lateinit var context: Context

        fun init(value: Application) {
            context = value
        }

        fun debugging(): Boolean {
            return true
        }

        fun getString(id: Int): String {
            return context.resources.getString(id)
        }

        fun getBoolean(id: Int): Boolean {
            return context.resources.getBoolean(id)
        }

    }
}