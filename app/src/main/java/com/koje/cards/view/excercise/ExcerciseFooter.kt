package com.koje.cards.view.excercise

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.StackEntry
import com.koje.cards.view.MainActivity
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.view.FrameLayoutBuilder


class ExcerciseFooter(val content: StackEntry) : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()
                setGravityCenterVertical()
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
                    setTextSizeSP(20)
                    setTextColorID(R.color.white)
                    setText("${content.stack.name} / ")
                }

                addTextView {
                    setTextSizeSP(20)
                    setTextColorID(R.color.white)
                    setText(Repository.score.get().toString())

                    addReceiver(Repository.score) {
                        setText(it.toString())
                    }
                }

                addFiller()

                addImageView {
                    setDrawableId(R.drawable.forwardicon)
                    setSizeDP(50)
                    setPaddingsDP(8, 8)

                    setOnClickListener {
                        MainActivity.content.set(Excercise())
                    }
                }
            }
        }
    }

}

