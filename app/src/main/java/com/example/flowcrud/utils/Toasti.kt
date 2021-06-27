package com.example.flowcrud.utils

import android.content.Context
import android.widget.Toast

fun Context.Toasti(string: String) {
    Toast.makeText(applicationContext, "$string", Toast.LENGTH_SHORT).show()
}