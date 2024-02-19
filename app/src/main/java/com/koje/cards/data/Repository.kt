package com.koje.cards.data

import com.koje.cards.view.Activity
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.App
import com.koje.framework.events.IntNotifier
import com.koje.framework.utils.Logger
import java.io.File

object Repository {

    val content = mutableListOf<Stack>()
    val path = "${App.context.filesDir.absolutePath}/cards"


    fun load() {
        content.clear()
        Thread {
            Logger.info(this, "data clear")
            Logger.info(this, "data path: $path")

            with(File(path)) {
                if (!isDirectory) {
                    mkdir()
                }
                listFiles()?.forEach {
                    if (it.isFile) {
                        Logger.info(this, "data file: ${it.name}")
                        try {
                            content.add(Stack(it.name))
                        } catch (e: Exception) {
                            Logger.info(this, "error:" + e)
                        }
                    }
                }
            }

            loadDefaultContent()
            Activity.content.set(StackList())
        }.start()
    }

    fun loadDefaultContent() {
        try {
            val manager = App.context.getAssets()
            manager.list("cards")?.forEach {
                if (!containsStack(it)) {
                    val result = Stack(it)
                    manager.open("cards/$it").bufferedReader().use {
                        result.loadFromJson(it.readText())
                    }
                    result.saveToJson()
                    content.add(result)
                }
            }
        } catch (e: Exception) {
            Logger.error(this, e.toString())
        }
    }

    private fun containsStack(name: String): Boolean {
        content.forEach {
            if (it.name == name) return true
        }
        return false
    }
}