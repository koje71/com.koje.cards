package com.koje.cards

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.data.Repository
import com.koje.cards.data.Stack
import com.koje.framework.App
import com.koje.framework.utils.Logger
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import java.io.File


class StackList : FrameLayoutBuilder.Editor {

    lateinit var list: RecyclerView

    override fun edit(target: FrameLayoutBuilder) {
        MainActivityHeader.content.set(StackListHeader())
        with(target){
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
                            setText("Start")
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

                var editor:EditText? = null

                addEditText {
                    setPaddingsDP(10,5)
                    setTextSizeSP(18)
                    setMarginsDP(0,5,0,0)
                    setText("Hinzufügen")
                    setBackgroundNull()
                    editor = view
                }

                addFiller()
                addImageView {
                    setDrawableId(R.drawable.addicon)
                    setSizeDP(50)
                    setPaddingsDP(5,5)

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

    fun createNewStack(name:String){
        if(name.length==0){
            return
        }

        Repository.data.forEach{
            if(it.name == name){
                return
            }
        }

        Repository.data.add(0,Stack(name))
        list.adapter?.notifyDataSetChanged()
    }

    companion object{
    }
}

