package com.koje.framework.graphics

import android.opengl.GLES20.GL_BLEND
import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.GL_DEPTH_BUFFER_BIT
import android.opengl.GLES20.GL_DEPTH_TEST
import android.opengl.GLES20.GL_LEQUAL
import android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA
import android.opengl.GLES20.GL_SRC_ALPHA
import android.opengl.GLES20.glBlendFunc
import android.opengl.GLES20.glClear
import android.opengl.GLES20.glClearColor
import android.opengl.GLES20.glDepthFunc
import android.opengl.GLES20.glEnable
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.view.MotionEvent
import android.view.View
import com.koje.framework.events.IntNotifier
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

open class Surface : GLSurfaceView.Renderer, View.OnTouchListener {

    val drawers = mutableListOf<Drawer>()
    val components = ComponentGroup(this)

    var loopTime = 0
    var loopStart = 0L
    var height = 0
    var width = 0
    var ratio = 0f

    var fpsCount = IntNotifier(0)
    var fpsCounter = 0
    var fpsTimer = 0

    var imageCount = IntNotifier(0)
    var imageCounter = 0

    var lastClickX = 0f
    var lastClickY = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(1f, 1f, 1f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        this.height = height
        this.width = width

        loopTime = 0
        loopStart = 0L
        glViewport(0, 0, width, height)
        ratio = height.toFloat() / width.toFloat()
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glDepthFunc(GL_LEQUAL)
        if (ratio >= 1.4f) {
            Matrix.orthoM(
                components.matrix,
                0,
                -0.5f,
                0.5f,
                -ratio / 2f,
                ratio / 2f,
                -10f,
                10f
            )
        } else {
            Matrix.orthoM(
                components.matrix,
                0,
                -0.5f * (1.4f / ratio),
                0.5f * (1.4f / ratio),
                -0.7f,
                0.7f,
                -10f,
                10f
            )
        }

        drawers.forEach {
            it.init()
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        val currentTime = System.currentTimeMillis()
        imageCounter = 0
        if (loopStart > 0) {
            components.draw()
            loopTime = (currentTime - loopStart).toInt()
            updateCounters()
        }
        loopStart = currentTime
    }

    fun updateCounters() {
        fpsTimer += loopTime
        fpsCounter++
        if (fpsTimer >= 1000) {
            fpsCount.set(fpsCounter)
            fpsTimer = 0
            fpsCounter = 0
            imageCount.set(imageCounter)
        }
    }

    fun createImageDrawer(drawable: Int, action: ImageDrawer.() -> Unit = {}): ImageDrawer {
        val result = ImageDrawer(drawable)
        action.invoke(result)
        drawers.add(result)
        return result
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            lastClickX = event.x
            lastClickY = event.y

            var posX = (event.x - width / 2) / width
            var posY = (-event.y + height / 2) / width

            if (ratio < 1.4f) {
                posX *= 1.4f / ratio
                posY *= 1.4f / ratio
            }
            onTouch(Position(posX, posY), event)
        }
        return true
    }

    open fun onTouch(position: Position, event: MotionEvent) {
    }


    fun <T : Component> addComponent(member: T, action: T.() -> Unit = {}) {
        components.addComponent(member, action)
    }

}