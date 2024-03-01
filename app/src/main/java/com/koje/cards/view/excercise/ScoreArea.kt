package com.koje.cards.view.excercise

import com.koje.cards.R
import com.koje.framework.view.FrameLayoutBuilder

class ScoreArea(val frame: ExcerciseFrame) : FrameLayoutBuilder.Editor {
    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                setPaddingsDP(10, 5)

                addLinearLayout {
                    setOrientationHorizontal()


                    addReceiver(frame.score) {
                        removeAllViews()

                        val lamps = mutableListOf<Boolean?>()
                        lamps.addAll(frame.history)
                        while (lamps.size < 20) lamps.add(null)
                        while (lamps.size > 20) lamps.removeAt(0)
                        lamps.forEach {
                            addView {
                                setLayoutWeight(1f)
                                setSizeDP(10)
                                setHeightDP(20)
                                setMarginsDP(1, 1, 1, 1)

                                setBackgroundGradient {
                                    setColorId(
                                        when (it) {
                                            true -> R.color.Solved
                                            false -> R.color.Failed
                                            else -> R.color.WhiteTransparent50
                                        }
                                    )
                                    setCornerRadius(3)
                                    setStroke(2, R.color.black)
                                }

                            }
                        }
                    }

                }
                addLinearLayout {
                    addTextView {
                        setTextColorID(R.color.black)
                        setTextSizeSP(18)
                        setPaddingsDP(3, 3)
                        addReceiver(frame.score) {
                            setText(
                                when (it) {
                                    1, -1 -> "$it Punkt"
                                    else -> "$it Punkte"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}