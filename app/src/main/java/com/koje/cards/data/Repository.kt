package com.koje.cards.data

import com.google.gson.Gson
import com.koje.cards.view.Activity
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.App
import com.koje.framework.utils.Logger
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


object Repository {

    val content = mutableListOf<Stack>()
    val path = "${App.context.filesDir.absolutePath}/cards"
    var source = ""


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

            if (content.size == 0) {
                loadDefaultContent()
            }

            loadFromNetwork()
            Activity.content.set(StackList())
        }.start()
    }

    fun loadFromNetwork(){
        if(source.length==0){
            return
        }

        val url = URL("http://$source")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val ins: InputStream = BufferedInputStream(urlConnection.inputStream)
            val inr = InputStreamReader(ins)
            val result: StackTransfer = Gson().fromJson(inr, StackTransfer::class.java)



        }catch(e:Exception){
            Logger.info(this,"error, reading from Network")
        } finally {
            urlConnection.disconnect()
        }
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