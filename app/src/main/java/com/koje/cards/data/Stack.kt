package com.koje.cards.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koje.framework.utils.BooleanPreference
import com.koje.framework.utils.Logger
import java.io.File
import java.lang.reflect.Type


class Stack(val name: String) {

    val content = mutableListOf<StackEntry>()
    var checked = BooleanPreference("checked-$name", false)

    init {
        loadFromStorage()
    }

    fun loadFromStorage() {
        try {
            val file = File("${Repository.path}/$name")
            if (!file.isFile) {
                file.printWriter().use {
                    it.println("")
                }
            }

            val json = file.bufferedReader().use {
                it.readText()
            }
            loadFromJson(json)
        } catch (e: Exception) {
            Logger.error(this, "parsing error")
        }
    }

    fun loadFromJson(json: String) {
        try {
            val listType: Type = object : TypeToken<List<StackEntryStorage?>?>() {}.type
            val sources: List<StackEntryStorage> = Gson().fromJson(json, listType)

            sources.forEach {
                content.add(StackEntry(this@Stack, it.a, it.b, it.c))
            }
        } catch (e: Exception) {
            Logger.error(this, "loading error")
        }
    }

    fun saveToJson() {
        Thread {
            val transferList = mutableListOf<StackEntryStorage>()
            this.content.forEach {
                transferList.add(StackEntryStorage(it.name, it.solution, it.score))
            }
            val json = Gson().toJson(transferList)

            val file = File("${Repository.path}/$name")
            file.bufferedWriter().use {
                it.write(json)
            }
        }.start()
    }

    fun delete() {
        val file = File("${Repository.path}/$name")
        file.delete()
        Repository.content.remove(this)
    }

    fun getScore(): Float {
        var countSum = 0
        content.forEach {
            countSum += it.score
        }

        if (content.size > 0) {
            return countSum.toFloat() / content.size
        }
        return 0f
    }

}