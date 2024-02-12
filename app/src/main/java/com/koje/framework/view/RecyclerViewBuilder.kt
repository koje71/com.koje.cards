package com.koje.framework.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewBuilder(override val view: RecyclerView) :
    ViewBuilder(view) {

    interface Editor : ViewEditor<RecyclerViewBuilder>

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter
    }

    fun setLinearLayoutManager() {
        view.setHasFixedSize(true)
        with(GridLayoutManager(view.context, 1)) {
            view.layoutManager = this
        }
    }

}