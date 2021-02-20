package com.aki.androidbpcode.utils.genericmethods

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.getCurrentPosition(): Int =
    (this.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
