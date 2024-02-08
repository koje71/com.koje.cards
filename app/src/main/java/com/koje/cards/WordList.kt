package com.koje.cards

import androidx.recyclerview.widget.LinearLayoutManager
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class WordList(val name:String) : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        MainActivityHeader.content.set(WordListHeader(name))
        with(target){
            addLinearLayout {
                setOrientationVertical()


                addScrollView {
                    addLinearLayout {
                        setOrientationVertical()

                        addRecyclerView {
                            val data = mutableListOf<WordPair>(
                                WordPair("huhu","hallo"),
                                WordPair("huhu1","hallo2")
                            )

                            val mLayoutManager = LinearLayoutManager(view.context)
                            view.setLayoutManager(mLayoutManager)

                            setAdapter(WordListAdapter(data))
                        }
                    }
                }
                addFiller()

                addRelativeLayout {
                    setGravityCenterRight()
                    setPaddingsDP(10,10)

                    addLinearLayout {
                        setOrientationHorizontal()
                        addTextView {
                            add(RoundCornerBackground(R.color.white))
                            setPaddingsDP(20,10,20,15)
                            setTextSizeSP(20)
                            setFontId(R.font.nunito_bold)
                            setMarginsDP(0,5,0,0)
                            setText("Export")
                        }
                        addTextView {
                            add(RoundCornerBackground(R.color.white))
                            setPaddingsDP(20,10,20,15)
                            setTextSizeSP(20)
                            setFontId(R.font.nunito_bold)
                            setMarginsDP(10,5,0,0)
                            setText("Import")
                        }
                        addTextView {
                            add(RoundCornerBackground(R.color.white))
                            setPaddingsDP(20,10,20,15)
                            setTextSizeSP(20)
                            setFontId(R.font.nunito_bold)
                            setMarginsDP(10,5,0,0)
                            setText("Zurück")

                            setOnClickListener {
                                MainActivity.content.set(StackList())
                            }
                        }
                    }
                }
            }
        }
    }

    fun addListEntry(target:LinearLayoutBuilder,value:String, solution:String){
        with(target){
            addLinearLayout {
                setOrientationHorizontal()

                addEditText {
                    setPaddingsDP(10,5)
                    setTextSizeSP(30)
                    setMarginsDP(0,5,0,0)
                    setText(value)
                    setWidthDP(100)
                    setLayoutWeight(1f)
                    setBackgroundNull()
                }

                addEditText {
                    setPaddingsDP(10,5)
                    setTextSizeSP(30)
                    setMarginsDP(0,5,0,0)
                    setText(solution)
                    setWidthDP(100)
                    setGravityRight()
                    setLayoutWeight(1f)
                    setBackgroundNull()
                }


            }
            addView {
                setHeightDP(3)
                setBackgroundColorId(R.color.BlackTransparent)
            }
        }
    }

}

