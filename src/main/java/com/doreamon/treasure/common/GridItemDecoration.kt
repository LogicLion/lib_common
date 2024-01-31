package com.doreamon.treasure.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doreamon.treasure.ext.dp

/**
 * @author wzh
 * @date 2023/10/20
 */
class GridItemDecoration(
    verticalSpacing: Int,
    horizontalSpacing: Int
) : RecyclerView.ItemDecoration() {

    private val verticalSpacing: Int
    private val horizontalSpacing: Int
    private val spacing = 15.dp
    private val includeEdge = false

    init {
        this.verticalSpacing = verticalSpacing.dp
        this.horizontalSpacing = horizontalSpacing.dp
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            val position = parent.getChildAdapterPosition(view) // Item position
            val column = position % spanCount // Item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }


    }
}