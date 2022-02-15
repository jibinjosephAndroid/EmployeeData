package com.jibin.codeTest.adapter

import android.widget.ImageView
import android.widget.TextView
import com.jibin.codeTest.database.model.Employee

interface OnItemClickListener {
    fun onItemClickListener(
        employee: Employee,
        imageViewProfile: ImageView,
        textViewName: TextView
    )
}