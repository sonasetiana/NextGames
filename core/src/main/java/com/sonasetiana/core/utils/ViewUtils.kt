package com.sonasetiana.core.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


fun NestedScrollView.onScroll(callback: EndlessScrollCallback) {
    this.setOnScrollChangeListener { v: NestedScrollView?, _: Int, y: Int, _: Int, oldY: Int ->
        if (v?.getChildAt(v.childCount - 1) != null) {
            if ((y >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) && y > oldY) {
                callback.onReachBottom()
            }
        }
    }
}

fun Context.itemDecoration(orientation: Int = LinearLayoutManager.VERTICAL) = DividerItemDecoration(this, orientation)

interface EndlessScrollCallback {
    fun onReachBottom()
}

fun TextInputEditText.rxSearchView() : Flowable<String> {
    val result = PublishSubject.create<String>()
    addTextChangedListener {
        result.onNext(it.toString())
    }
    return result.toFlowable(BackpressureStrategy.BUFFER)
}