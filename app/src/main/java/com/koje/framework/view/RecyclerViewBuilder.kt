package com.koje.framework.view

import android.graphics.drawable.GradientDrawable.Orientation
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.WordListAdapter

class RecyclerViewBuilder(override val view: RecyclerView) :
    ViewBuilder(view) {

    interface Editor : ViewEditor<RecyclerViewBuilder>

    fun setAdapter(adapter: RecyclerView.Adapter<*>){
        view.adapter = adapter
    }

    fun setLinearLayoutManager(){
        view.setHasFixedSize(true)
        with(GridLayoutManager(view.context,1)){
            view.layoutManager = this
        }
    }

}