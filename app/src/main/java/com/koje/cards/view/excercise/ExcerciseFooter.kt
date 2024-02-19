package com.koje.cards.view.excercise

import com.koje.cards.R
import com.koje.cards.view.Activity
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.view.FrameLayoutBuilder


class ExcerciseFooter(val frame: ExcerciseFrame) : FrameLayoutBuilder.Editor {

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
                        Activity.content.set(StackList())
                    }
                }

                addFiller()

                addTextView {
                    setTextSizeSP(20)
                    setTextColorID(R.color.white)

                    addReceiver(frame.content) {
                        setText("${it.content.stack.name}")
                    }
                }

                addFiller()

                addImageView {
                    setDrawableId(R.drawable.forwardicon)
                    setSizeDP(50)
                    setPaddingsDP(8, 8)

                    setOnClickListener {
                        frame.content.set(Excercise(frame))
                    }
                }
            }
        }
    }
}

