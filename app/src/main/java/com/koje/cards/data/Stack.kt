package com.koje.cards.data

import com.koje.cards.StackListEntry
import com.koje.framework.App
import com.koje.framework.utils.Logger
import java.io.File

class Stack(val name:String) {

    val content = mutableListOf<StackEntry>()

    init{
        Logger.info(this,"create: $name")
    }

    fun load(){
        File("${Repository.path}/$name").bufferedReader().forEachLine { line ->
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

    }

}