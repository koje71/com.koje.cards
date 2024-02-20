package com.koje.cards.data

import android.content.Context
import android.net.ConnectivityManager
import com.koje.framework.App
import com.koje.framework.utils.Logger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.util.Collections

object Network {

    private var server: ServerSocket? = null

    fun stop(){
        server?.close()
    }

    fun publish(stack: Stack) {
        connect()

        Thread {
            try {
                server?.close()
                val listener = ServerSocket(8888)
                this.server = listener
                while (true) {
                    var socket = listener.accept()
                    var reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    Logger.info("ip-address", reader.readLine())

                    var items = mutableListOf<StackEntryStorage>()
                    stack.content.forEach {
                        items.add(StackEntryStorage(it.name,it.solution,0))
                    }
                    var transfer = StackTransfer(stack.name,items)


                    var writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()))
                    stack.content.forEach {
                        writer.println("huhu")
                        Logger.info("ip-address", "huhu")
                    }
                    writer.close()
                }
            } catch (e: Exception) {
                Logger.info("ip-address:", e.toString())
            }
        }.start()
    }


    fun connect() {
        val cm = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (cm != null) {
            for (net in cm.getAllNetworks()) {
                cm.bindProcessToNetwork(net)
            }
        }
    }

    fun getLocalIpAdress(): String {
        for (intf in Collections.list(NetworkInterface.getNetworkInterfaces())) {
            val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
            for (addr in addrs) {
                if (!addr.isLoopbackAddress) {
                    listOf("192.", "172.", "10.").forEach {
                        if (addr.hostAddress.startsWith(it)) {
                            return addr.hostAddress
                        }
                    }
                }
            }
        }
        return ("")
    }

}