package com.koje.cards.data

import com.koje.cards.view.MainActivity
import com.koje.cards.view.StackList
import com.koje.framework.App
import com.koje.framework.events.IntNotifier
import com.koje.framework.utils.Logger
import java.io.File

object Repository {

    val content = mutableListOf<Stack>()
    val path = "${App.context.filesDir.absolutePath}/cards"
    var score = IntNotifier(0)

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
            MainActivity.content.set(StackList())
        }.start()
    }

}