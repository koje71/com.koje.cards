package com.koje.cards

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.data.Stack
import com.koje.cards.data.StackEntry
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class WordList(val stack: Stack) : FrameLayoutBuilder.Editor {

    lateinit var list: RecyclerView

    override fun edit(target: FrameLayoutBuilder) {
        MainActivityHeader.content.set(WordListHeader(stack.name))
        with(target){
            addLinearLayout {
                setOrientationVertical()
                setWidthMatchParent()

                addCreateEntry(this)
                addScrollView {
                    setWidthMatchParent()
                        addRecyclerView {
                            setWidthMatchParent()
                            setLinearLayoutManager()
                            setAdapter(WordListAdapter(stack))
                            list = this.view
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

    @SuppressLint("NotifyDataSetChanged")
    fun addCreateEntry(target:LinearLayoutBuilder){
        with(target){
            addLinearLayout {
                setOrientationHorizontal()

                var edit1: EditText? = null
                var edit2: EditText? = null

                addEditText {
                    setPaddingsDP(10,5)
                    setTextSizeSP(18)
                    setMarginsDP(0,5,0,0)
                    setText("Vokabel")
                    setBackgroundNull()
                    setWidthDP(100)
                    setLayoutWeight(1f)
                    edit1 = this.view
                }

                addEditText {
                    setPaddingsDP(10,5)
                    setTextSizeSP(18)
                    setMarginsDP(0,5,0,0)
                    setText("Übersetzung")
                    setBackgroundNull()
                    setWidthDP(100)
                    setLayoutWeight(1f)
                    edit2 = this.view
                }

                addImageView {
                    setDrawableId(R.drawable.addicon)
                    setSizeDP(50)
                    setPaddingsDP(5,5)

                    setOnClickListener {
                        createNewStackEntry(edit1?.text.toString(),edit2?.text.toString())
                    }
                }
            }
            addView {
                setHeightDP(3)
                setBackgroundColorId(R.color.BlackTransparent)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun createNewStackEntry(word1:String, word2:String){
        with(stack){
            content.forEach {
                if(it.name == word1){
                    return
                }
            }
            content.add(0, StackEntry(word1,word2,0))
            save()
            list.adapter?.notifyDataSetChanged()
        }
    }
}

