package com.koje.cards.data

import com.koje.framework.utils.Logger
import java.io.File

class Stack(val name:String) {

    val content = mutableListOf<StackEntry>()

    init{
        load()
    }

    fun load(){
        val file = File("${Repository.path}/$name")
        if(!file.isFile){
            file.printWriter().use { out ->
                out.println("")
            }
        }

        file.bufferedReader().forEachLine { line ->
            val fields = line.split("#")
            if (fields.size > 2) {
                content.add(
                    StackEntry(
                        fields[0],
                        fields[1],
                        Integer.parseInt(fields[2])
                    )
                )
            }
        }
    }

    fun save(){
        val file = File("${Repository.path}/$name")
        file.printWriter().use { out ->
            content.forEach {
                out.println("${it.name}#${it.solution}#${it.count}")
            }
        }

    }

}