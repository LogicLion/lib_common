package com.doreamon.treasure.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doreamon.treasure.ext.dp

/**
 * @author wzh
 * @date 2023/10/20
 */
class LinearItemDecoration(
    verticalSpacing: Int,
    horizontalSpacing: Int,
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0
) : RecyclerView.ItemDecoration() {

    private val verticalSpacing: Int
    private val horizontalSpacing: Int
    private val left: Int
    private val top: Int
    private val right: Int
    private val bottom: Int

    init {
        this.verticalSpacing = verticalSpacing.dp
        this.horizontalSpacing = horizontalSpacing.dp
        this.left = left.dp
        this.top = top.dp
        this.right = right.dp
        this.bottom = bottom.dp
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = parent.adapter?.itemCount ?: 0
        val position = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                if (position == 0) {
                    //第一个
                    outRect.top = top
                } else {
                    outRect.top = verticalSpacing
                }
                if (position == itemCount - 1) {
                    //最后一个
                    outRect.bottom = bottom
                }
                outRect.left = left
                outRect.right = right
            } else {
                if (position == 0) {
                    outRect.left = left
                } else {
                    outRect.left = horizontalSpacing
                }

                if (position == itemCount - 1) {
                    outRect.right = right
                }

                outRect.top = top
                outRect.bottom = bottom
            }
        }
    }
}