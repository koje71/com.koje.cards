package com.koje.cards.view.wordlist

import com.koje.cards.R
import com.koje.cards.data.Stack
import com.koje.cards.view.Activity
import com.koje.cards.view.general.EmptyView
import com.koje.cards.view.general.RoundCornerButtonFormat
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.view.FrameLayoutBuilder


class DeleteDialog(val stack: Stack) : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addRelativeLayout {
                setGravityCenter()
                setSizeMatchParent()
                setBackgroundColorId(R.color.DialogTransparent)

                setOnClickListener {
                    close()
                }

                addLinearLayout {
                    setOrientationVertical()
                    setWidthDP(300)
                    setPaddingsDP(10, 10, 10, 10)
                    setMarginsDP(5, 5, 5, 5)
                    setBackgroundGradient {
                        setColorId(R.color.white)
                        setCornerRadius(10)
                        setStroke(3, R.color.black)
                    }

                    addTextView {
                        setPaddingsDP(5, 10, 10, 20)
                        setText("Wollen Sie wirklich den kompletten Kartenstapel löschen?")
                        setTextSizeSP(24)
                    }

                    addLinearLayout {
                        setOrientationHorizontal()

                        addFiller()

                        addTextView {
                            setText("Nein")
                            setWidthDP(100)
                            setGravityCenter()
                            add(RoundCornerButtonFormat())
                            setOnClickListener {
                                close()
                            }
                        }

                        addTextView {
                            setText("Ja")
                            setWidthDP(100)
                            setGravityCenter()
                            add(RoundCornerButtonFormat())
                            setOnClickListener {
                                process()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun process() {
        stack.delete()
        Activity.content.set(StackList())
        close()
    }

    private fun close() {
        Activity.overlay.set(EmptyView())
    }
}

