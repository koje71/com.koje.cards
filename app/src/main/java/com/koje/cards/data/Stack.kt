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
        load()
    }

    fun bindOnNetwork(){

    }
    fun load() {
        try {
            val file = File("${Repository.path}/$name")
            if (!file.isFile) {
                file.printWriter().use { out ->
                    out.println("")
                }
            }

            val bufferedReader = file.bufferedReader()
            val json = bufferedReader.use {
                it.readText()
            }

            val gson = Gson()
            val listType: Type = object : TypeToken<List<StackEntryTransfer?>?>() {}.type
            val sources: List<StackEntryTransfer> = gson.fromJson(json, listType)

            sources.forEach {
                content.add(StackEntry(this@Stack, it.a, it.b, it.c))
            }
        }catch (e:Exception){
            Logger.info(this,"parsing error")
        }
    }

    fun loadOld() {
        val file = File("${Repository.path}/$name")
        if (!file.isFile) {
            file.printWriter().use { out ->
                out.println("")
            }
        }

        file.bufferedReader().forEachLine { line ->
            val fields = line.split("#")
            if (fields.size > 2 && fields[0] != "") {
                content.add(
                    StackEntry(
                        this@Stack,
                        fields[0],
                        fields[1],
                        Integer.parseInt(fields[2])
                    )
                )
            }
        }
    }

    fun save() {
        Thread{
            val saver = mutableListOf<StackEntryTransfer>()
            this.content.forEach {
                saver.add(StackEntryTransfer(it.name,it.solution,it.score))
            }
            val gson = Gson()
            val json = gson.toJson(saver)

            val file = File("${Repository.path}/$name")
            file.bufferedWriter().use { out ->
                    out.write(json)
            }
        }.start()


    }

    fun saveOld() {
        val file = File("${Repository.path}/$name")
        file.printWriter().use { out ->
            content.forEach {
                out.println("${it.name}#${it.solution}#${it.score}")
            }
        }
    }

    fun delete(){
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