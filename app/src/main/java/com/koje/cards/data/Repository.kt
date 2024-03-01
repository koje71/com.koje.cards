package com.koje.cards.data

import com.google.gson.Gson
import com.koje.cards.view.Activity
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.App
import com.koje.framework.utils.Logger
import java.io.BufferedInputStream
import java.io.BufferedReader
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
            loadNetworkContent()

            Activity.content.set(StackList())
        }.start()
    }

    fun loadNetworkContent() {

        /*
        Wenn der User einen QR Code scannt, wird der Content von dort geholt.
         */

        if (source.length == 0) {
            return
        }

        val url = URL("http://${source}_cards.json")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            with(urlConnection){
                addRequestProperty("Connection", "close");
                addRequestProperty("User-Agent","Mozilla/5.0 (Java/UploadVertretungsplan;)");
                addRequestProperty("Accept","text/xml,application/json,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
                addRequestProperty("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");

                val stream: InputStream = BufferedInputStream(inputStream)
                val reader = BufferedReader(InputStreamReader(stream))

                var line: String? = null
                var data = StringBuilder()
                while (reader.readLine().also { line = it } != null) {
                    data.append(line + "\n")
                    Logger.info(this,line + "")
                }
                val result: StackTransfer = Gson().fromJson(data.toString(), StackTransfer::class.java)

                var stack: Stack? = null;
                Repository.content.forEach {
                    if (it.name == result.name) {
                        stack = it
                    }
                }

                if (stack == null) {
                    with(Stack(result.name)) {
                        stack = this
                        this@Repository.content.add(this)
                    }
                }

                result.content.forEach {
                    stack?.addEntry(StackEntry(stack!!, it.a, it.b, it.c))
                }

                stack?.saveToJson()
            }
        } catch (e: Exception) {
            Logger.info(this, "read error: $e")
        } finally {
            urlConnection.disconnect()
        }

        source = ""
    }

    fun loadDefaultContent() {

        /*
        Default Content wird in den Assets mitgebracht, damit der User initial einige
        Wörterbücher zur Verfügung hat.
         */

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