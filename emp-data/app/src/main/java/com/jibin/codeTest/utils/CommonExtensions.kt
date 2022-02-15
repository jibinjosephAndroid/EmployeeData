package com.jibin.codeTest.utils

import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.navigation.*
import com.bumptech.glide.request.RequestOptions
import com.jibin.codeTest.R

fun ImageView.loadImage(url: String?) {
    GlideApp.with(this)
        .load(url)
        .placeholder(R.drawable.ic_user_placeholder_icon)
        .apply(RequestOptions.circleCropTransform())
        .circleCrop()
        .into(this)
}


fun NavController.navigateSafe(directions: NavDirections) {
    // Get action by ID. If action doesn't exist on current node, return.
    val action = (currentDestination ?: graph).getAction(directions.actionId) ?: return
    var destId = action.destinationId
    val dest = graph.findNode(destId)
    if (dest is NavGraph) {
        // Action destination is a nested graph, which isn't a real destination.
        // The real destination is the start destination of that graph so resolve it.
        destId = dest.startDestination
    }
    if (currentDestination?.id != destId) {
        navigate(directions)
    }
}

fun emptyString(): String{
    return ""
}

