package com.koje.cards

import com.koje.framework.events.Notifier
import com.koje.framework.view.BaseActivity
import com.koje.framework.view.FrameLayoutBuilder

class MainActivity : BaseActivity() {

    override fun createLayout(target: FrameLayoutBuilder) {
        with(target){
            addFrameLayout {
                addLinearLayout {
                    setOrientationVertical()
                    add(MainActivityHeader())
                    addFrameLayout {
                        setLayoutWeight(1f)
                        setWidthMatchParent()
                        addReceiver(content){
                            replaceWithFade(it)
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
    }

    companion object{
        val content = Notifier<FrameLayoutBuilder.Editor>(StackList())
        val overlay = Notifier<FrameLayoutBuilder.Editor>(EmptyView())
    }
}

