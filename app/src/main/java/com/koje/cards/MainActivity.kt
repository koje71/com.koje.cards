package com.koje.cards

import android.app.ActionBar
import android.view.WindowManager
import com.koje.cards.data.Repository
import com.koje.cards.data.Stack
import com.koje.cards.data.StackEntry
import com.koje.framework.App
import com.koje.framework.events.Notifier
import com.koje.framework.utils.Logger
import com.koje.framework.view.BaseActivity
import com.koje.framework.view.FrameLayoutBuilder
import java.io.File

class MainActivity : BaseActivity() {


    override fun createLayout(target: FrameLayoutBuilder) {
        with(window) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = target.getColorFromID(R.color.black)
            navigationBarColor = target.getColorFromID(R.color.black)
        }

        with(target){
            addFrameLayout {
                addLinearLayout {
                    setOrientationVertical()
                    add(MainActivityHeader())
                    addFrameLayout {
                        setLayoutWeight(1f)
                        setWidthMatchParent()
                        addImageView {
                            setDrawableId(R.drawable.background)
                            setScaleTypeCenterCrop()
                        }
                        addFrameLayout {
                            setLayoutWeight(1f)
                            setWidthMatchParent()
                            addReceiver(content){
                                replaceWithFade(it)
                            }
                        }
                    }

                }

                addFrameLayout {
                    setLayoutWeight(1f)
                    setWidthMatchParent()
                    addReceiver(overlay){
                        replaceWithFade(it)
                    }
                }
            }
        }
        Repository.load()
    }


    companion object{
        val content = Notifier<FrameLayoutBuilder.Editor>(EmptyView())
        val overlay = Notifier<FrameLayoutBuilder.Editor>(EmptyView())
    }
}

