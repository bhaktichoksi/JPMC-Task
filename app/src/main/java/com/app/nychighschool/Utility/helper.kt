package com.app.nychighschool.Utility

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            ?: return false
    return try {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
            false
        } else true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun collapse(v: View) {
    val initialHeight: Int = v.getMeasuredHeight()
    val va = ValueAnimator.ofInt(initialHeight, 0)
    va.addUpdateListener { animation ->
        v.getLayoutParams().height = animation.animatedValue as Int
        v.requestLayout()
    }
    va.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            v.setVisibility(View.GONE)
        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }

    })
    va.duration = 300
    va.interpolator = DecelerateInterpolator()
    va.start()
}

fun expand(v: View) {
    v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = v.getMeasuredHeight()

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.getLayoutParams().height = 1
    v.setVisibility(View.VISIBLE)
    val va = ValueAnimator.ofInt(1, targetHeight)
    va.addUpdateListener { animation ->
        v.getLayoutParams().height = animation.animatedValue as Int
        v.requestLayout()
    }
    va.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }

    })
    va.duration = 300
    va.interpolator = OvershootInterpolator()
    va.start()
}