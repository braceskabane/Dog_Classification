package com.dicoding.asclepius.view.dashboard.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            // Add space to the right for all items except the last one
            if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
                right = spaceSize
            }
        }
    }
}