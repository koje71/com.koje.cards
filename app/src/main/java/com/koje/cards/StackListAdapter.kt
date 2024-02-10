package com.koje.cards

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.data.Repository
import com.koje.framework.App
import com.koje.framework.utils.Logger
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.framework.view.RelativeLayoutBuilder
import java.io.File


class StackListAdapter() :
    RecyclerView.Adapter<StackListAdapter.ViewHolder>() {

    val content = Repository.data

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById(nameId) as TextView
        var wordCount = view.findViewById(wordCountId) as TextView
        var progress = view.findViewById(progressId) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        with(LinearLayoutBuilder(LinearLayout(parent.context))) {
                setOrientationHorizontal()
                addImageView {
                    setMarginsDP(10,8,5,0)
                    setDrawableId(R.drawable.editicon)
                    setSizeDP(40)
                    setOnClickListener {
                        MainActivity.content.set(WordList("latein"))
                    }
                }

                addTextView {
                    setViewId(nameId)
                    setPaddingsDP(10,5)
                    setTextSizeSP(30)
                    setLayoutWeight(1f)
                    setMarginsDP(0,5,0,0)
                }


                addLinearLayout {
                    setOrientationVertical()
                    setPaddingsDP(0,10,10,0)
                    addTextView {
                        setViewId(wordCountId)
                        setText("1256")
                    }
                    addTextView {
                        setViewId(progressId)
                        setText("99,7%")
                    }
                }

                addCheckbox {
                    setMarginsDP(5,0,10,15)
                    view.scaleX = 1.5f
                    view.scaleY = 1.5f
                }

            return ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            name.setText( content[position].name)
            wordCount.setText("234")
        }
    }

    companion object{
        val nameId = View.generateViewId()
        val wordCountId = View.generateViewId()
        val progressId = View.generateViewId()
    }

}