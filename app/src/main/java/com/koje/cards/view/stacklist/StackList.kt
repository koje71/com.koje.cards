package com.koje.cards.view.stacklist

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.Stack
import com.koje.cards.view.MainActivityFooter
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class StackList : FrameLayoutBuilder.Editor {

    lateinit var list: RecyclerView

    override fun edit(target: FrameLayoutBuilder) {
        MainActivityFooter.content.set(StackListFooter())
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
                    setPaddingsDP(10, 10)

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

