package com.koje.cards.view.wordlist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.Stack
import com.koje.cards.view.Activity
import com.koje.cards.view.general.EmptyView
import com.koje.cards.view.general.RoundCornerButtonFormat
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.App
import com.koje.framework.utils.Logger
import com.koje.framework.view.FrameLayoutBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.util.Collections


class ShareDialog(val stack: Stack) : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        with(target){
            addRelativeLayout {
                setGravityCenter()
                setSizeMatchParent()
                setBackgroundColorId(R.color.DialogTransparent)

                setOnClickListener {
                    close()
                }

                addLinearLayout {
                    setOrientationVertical()
                    setPaddingsDP(10, 10, 10, 10)
                    setMarginsDP(5, 5, 5, 5)
                    setBackgroundGradient {
                        setColorId(R.color.white)
                        setCornerRadius(10)
                        setStroke(3, R.color.black)
                    }

                    addTextView {
                        setPaddingsDP(5, 10, 10, 0)
                        setText("Scanne den QR Code mit einem Gerät im gleichen Netzwerk!")
                        setTextSizeSP(24)
                    }

                    addImageView {
                        setImageBitmap(getBitmap())
                        setPaddingsDP(15, 15)
                        setSizeDP(300)
                    }

                    addLinearLayout {
                        setOrientationHorizontal()

                        addFiller()

                        addTextView {
                            setText("Fertig")
                            setWidthDP(100)
                            setGravityCenter()
                            add(RoundCornerButtonFormat())
                            setOnClickListener {
                                close()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun process(){
        stack.delete()
        Activity.content.set(StackList())
        close()
    }

    private fun close(){
        Activity.overlay.set(EmptyView())
    }

    var server: ServerSocket? = null

    fun publish(stack:Stack){
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

                    var writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()))
                    stack.content.forEach {
                        writer.println("huhu")
                        Logger.info("ip-address","huhu")
                    }
                    writer.close()
                }
            } catch (e: Exception) {
                Logger.info("ip-address:", e.toString())
            }
        }.start()

    }


    fun connect(){
        val cm = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (cm != null) {
            for (net in cm.getAllNetworks()) {
                cm.bindProcessToNetwork(net)
            }
        }
    }

    fun getLocalIpAdress():String{
        for (intf in Collections.list(NetworkInterface.getNetworkInterfaces())) {
            val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
            for (addr in addrs) {
                if (!addr.isLoopbackAddress) {
                    listOf("192.","172.","10.").forEach {
                        if(addr.hostAddress.startsWith(it)){
                            return addr.hostAddress
                        }
                    }
                }
            }
        }
        return("")
    }

    fun getBitmap(): Bitmap {
        val size = 512
        val content = "kocards://content?source=${getLocalIpAdress()}"
        val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

}

