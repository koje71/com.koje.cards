package com.koje.cards.view

import android.content.Intent
import android.net.Uri
import android.view.WindowManager
import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.view.general.EmptyView
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.events.Notifier
import com.koje.framework.view.BaseActivity
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder

class Activity : BaseActivity() {

    override fun createLayout(target: FrameLayoutBuilder) {
        handleIntent(intent)
        with(window) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = target.getColorFromID(R.color.black)
            navigationBarColor = target.getColorFromID(R.color.black)
        }

        with(target) {
            addFrameLayout {
                addLinearLayout {
                    setOrientationVertical()
                    addHeader(this)
                    addContent(this)
                    addFooter(this)
                }
                addFrameLayout {
                    setLayoutWeight(1f)
                    setWidthMatchParent()
                    addReceiver(overlay) {
                        replaceWithFade(it)
                    }
                }
            }
        }
        Repository.load()
    }

    private fun addHeader(target: LinearLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                setBackgroundColorId(R.color.TitleBackground)
                addLinearLayout {
                    setOrientationHorizontal()
                    setGradientBackgroundVertical(
                        R.color.black,
                        R.color.TitleBackground
                    )
                    setGravityBottom()

                    addTextView {
                        setPaddingsDP(10, 0)
                        setTextSizeSP(40)
                        setTextColorID(R.color.white)
                        setFontId(R.font.nunito_bold)
                        setText("Lernkarten")

                        setOnClickListener {
                            content.set(StackList())
                        }
                    }
                    addFiller()
                    addImageView {
                        setDrawableId(R.drawable.owl)
                        setSizeDP(40)
                        setMarginsDP(0,0,5,5)
                    }
                }
                addView {
                    setHeightDP(3)
                    setWidthMatchParent()
                    setBackgroundColorId(R.color.WhiteTransparent)
                }
            }
        }
    }

    private fun addContent(target: LinearLayoutBuilder) {
        with(target) {
            addFrameLayout {
                setLayoutWeight(1f)
                setWidthMatchParent()
                addImageView {
                    //https://unsplash.com/de/fotos/blick-auf-einen-nebelverhangenen-berg-M6z6yJUxxd8
                    setDrawableId(R.drawable.bgr01)
                    setScaleTypeCenterCrop()
                }

                addFrameLayout {
                    setLayoutWeight(1f)
                    setWidthMatchParent()
                    addReceiver(content) {
                        replaceWithFade(it)
                    }
                }
            }
        }
    }


    private fun addFooter(target: LinearLayoutBuilder) {
        with(target) {
            addFrameLayout {
                setHeightDP(60)
                setWidthMatchParent()
                setGradientBackgroundVertical(
                    R.color.TitleBackground,
                    R.color.black
                )
                setPaddingsDP(5, 0)
                addReceiver(footer) {
                    replace(it)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    fun handleIntent(intent: Intent?) {
        if (intent != null) {
            val data: Uri? = intent?.data
            if (data != null) {
                val source = data.getQueryParameter("source")
                if (source != null) {
                    Repository.source = source
                }
            }
        }
    }

    companion object {
        val content = Notifier<FrameLayoutBuilder.Editor>(EmptyView())
        val footer = Notifier<FrameLayoutBuilder.Editor>(EmptyView())
        val overlay = Notifier<FrameLayoutBuilder.Editor>(EmptyView())
    }
}

