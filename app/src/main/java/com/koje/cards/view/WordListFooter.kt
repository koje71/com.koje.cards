package com.koje.cards.view

import com.koje.cards.R
import com.koje.framework.utils.Logger
import com.koje.framework.view.FrameLayoutBuilder


class WordListFooter : FrameLayoutBuilder.Editor {

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
                        MainActivity.content.set(StackList())
                    }
                }

                addFiller()

                addTextView {
                    add(RoundCornerButtonFormat())
                    setText("Import")
                    setOnClickListener {
                        Logger.info(this, "huhu")
                    }
                }

                addTextView {
                    add(RoundCornerButtonFormat())
                    setText("Export")
                    setOnClickListener {
                        Logger.info(this, "huhu")
                    }
                }
            }
        }
    }

}

