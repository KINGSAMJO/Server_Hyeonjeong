package com.example.seminar_task1

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val offsets : Int, private val colors : String) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(offsets,offsets,offsets,offsets)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val paint = Paint().apply {
            color = Color.parseColor(colors)
        }
        for(i in 0 until parent.childCount){
            val child = parent.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams
            val divTop = child.bottom+ param.bottomMargin
            val divBottom = divTop + 5
            if(i != parent.childCount ){
                c.drawRect(child.left.toFloat() , divTop.toFloat(), child.right.toFloat() ,divBottom.toFloat() , paint)
            }
        }
    }
}