package com.koje.cards.view.wordlist

import com.koje.cards.R
import com.koje.cards.data.Stack
import com.koje.cards.view.Activity
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.view.FrameLayoutBuilder


class WordListFooter(val stack: Stack) : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()
                setPaddingsDP(5, 5)
                addImageView {
                    setDrawableId(R.drawable.stopicon)
                    setSizeDP(50)
                    setPaddingsDP(8, 8)
                    setOnClickListener {
                        Activity.content.set(StackList())
                    }
                }
                addFiller()
                addImageView {
                    setDrawableId(R.drawable.removeiconwhite)
                    setSizeDP(50)
                    setPaddingsDP(10, 10)
                    setOnClickListener {
                        Activity.overlay.set(DeleteDialog(stack))
                    }
                }
                addImageView {
                    setDrawableId(R.drawable.share)
                    setSizeDP(50)
                    setPaddingsDP(10, 10)
                    setOnClickListener {
                        Activity.overlay.set(ShareDialog(stack))
                    }
                }
            }
        }
    }
}

