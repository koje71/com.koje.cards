package com.koje.cards.view

import com.koje.cards.R
import com.koje.cards.view.general.EmptyView
import com.koje.framework.events.Notifier
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder

class MainActivityFooter : LinearLayoutBuilder.Editor {
    override fun edit(target: LinearLayoutBuilder) {
        with(target) {
            addFrameLayout {
                setHeightDP(60)
                setWidthMatchParent()
                setGradientBackgroundVertical(
                    R.color.TitleBackground,
                    R.color.black
                )
                setPaddingsDP(5, 0)
                addReceiver(content) {
                    replace(it)
                }
            }
        }
    }

    companion object {
        val content = Notifier<FrameLayoutBuilder.Editor>(EmptyView())
    }
}