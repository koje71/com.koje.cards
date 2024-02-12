package com.koje.cards.view

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.Stack
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class StackList : FrameLayoutBuilder.Editor {

    lateinit var list: RecyclerView

    override fun edit(target: FrameLayoutBuilder) {
        MainActivityHeader.content.set(StackListHeader())
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                addCreateEntry(this)
                addScrollView {
                    addRecyclerView {
                        setWidthMatchParent()
                        setLinearLayoutManager()
                        setAdapter(StackListAdapter())
                        list = view
                    }
                }
                addFiller()

                addLinearLayout {
                    setOrientationHorizontal()
                    setBackgroundColorId(R.color.TitleBackground)
                    setPaddingsDP(10, 10)
                    addFiller()

                    addTextView {
                        add(RoundCornerButtonFormat())
                        setText("Start")

                        setOnClickListener {
                            Repository.score=0
                            MainActivity.content.set(Excercise())
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCreateEntry(target: LinearLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()

                var editor: EditText? = null

                addEditText {
                    setPaddingsDP(10, 5)
                    setTextSizeSP(18)
                    setMarginsDP(0, 5, 0, 0)
                    setText("Hinzufügen")
                    setBackgroundNull()
                    editor = view
                }

                addFiller()
                addImageView {
                    setDrawableId(R.drawable.addicon)
                    setSizeDP(50)
                    setPaddingsDP(5, 5)

                    setOnClickListener {
                        createNewStack(editor?.text.toString())
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
    private fun createNewStack(name: String) {
        if (name.length == 0) {
            return
        }
        Repository.content.forEach {
            if (it.name == name) {
                return
            }
        }
        Repository.content.add(0, Stack(name))
        list.adapter?.notifyDataSetChanged()
    }

}

