package com.koje.cards.view.wordlist

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.R
import com.koje.cards.data.Stack
import com.koje.framework.view.LinearLayoutBuilder


class WordListAdapter(val stack: Stack) :
    RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    val content = stack.content


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var col1: TextView = view.findViewById(col1Id) as TextView
        var col2: TextView = view.findViewById(col2Id) as TextView
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        with(LinearLayoutBuilder(LinearLayout(parent.context))) {
            setOrientationHorizontal()

            addLinearLayout {
                setLayoutWeight(1f)
                setOrientationHorizontal()
                setGravityCenterVertical()
                setMarginsDP(0, 3, 0, 0)

                addTextView {
                    setViewId(col1Id)
                    setLayoutWeight(1f)
                    setPaddingsDP(10, 5)
                    setTextSizeSP(22)
                }

                addFiller()
                addTextView {
                    setViewId(col2Id)
                    setLayoutWeight(1f)
                    setPaddingsDP(10, 5)
                    setGravityRight()
                    setTextSizeSP(22)
                }
            }



            addImageView {
                setDrawableId(R.drawable.removeicon)
                setSizeDP(40)
                setPaddingsDP(5, 8,8,5)
                setMarginsDP(0,0,3,0)

                setOnClickListener {
                    val name = parent.findViewById<TextView>(col1Id).text
                    val iterator = stack.content.iterator()
                    while (iterator.hasNext()) {
                        val current = iterator.next()
                        if (current.name == name) {
                            iterator.remove()
                        }
                        stack.save()
                    }
                    notifyDataSetChanged()
                }
            }

            return ViewHolder(view)
        }
    }


    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.col1.setText(content[position].name)
        holder.col2.setText(content[position].solution)
    }

    companion object {
        val col1Id = View.generateViewId()
        val col2Id = View.generateViewId()
    }

}