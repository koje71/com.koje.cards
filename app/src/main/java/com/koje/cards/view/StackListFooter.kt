package com.koje.cards.view

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.framework.view.FrameLayoutBuilder


class StackListFooter : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()
                setPaddingsDP(5, 5)
                addFiller()

                addImageView {
                    setDrawableId(R.drawable.forwardicon)
                    setSizeDP(50)
                    setPaddingsDP(8, 8)

                    setOnClickListener {
                        Repository.score.set(0)
                        MainActivity.content.set(Excercise())
                    }
                }

            }
        }
    }

}

