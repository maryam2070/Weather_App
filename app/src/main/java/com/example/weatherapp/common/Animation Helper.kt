package com.example.weatherapp.common

import android.animation.Animator
import android.animation.Animator.DURATION_INFINITE
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnPause
import androidx.core.animation.doOnRepeat

fun sunAnimation(iv: ImageView)
{
    val animator = ObjectAnimator.ofFloat(iv, View.ROTATION, 405f)
    animator.duration=4000
    animator.repeatCount = INFINITE
    animator.repeatMode= ValueAnimator.RESTART
    animator.start()

}

fun moonAnimation(iv: ImageView)
{
    val animator = ObjectAnimator.ofFloat(iv, View.ROTATION, 50f)
    animator.duration=1000
    animator.repeatCount = INFINITE
    animator.repeatMode= ValueAnimator.REVERSE
    animator.start()
}

fun cloudAnimation(iv:ImageView)
{
    val animator = ObjectAnimator.ofFloat(iv, View.TRANSLATION_X, -20f)
    animator.duration=1000
    animator.repeatCount = INFINITE
    animator.repeatMode= ValueAnimator.REVERSE
    animator.start()
}
fun rainAnimation(iv: ImageView)
{
    val animator = ObjectAnimator.ofFloat(iv, View.TRANSLATION_Y, 70f)
    animator.duration=1500
    animator.repeatCount = INFINITE
    animator.repeatMode= ValueAnimator.RESTART
    animator.start()

    val animator2 = ObjectAnimator.ofFloat(iv, View.ALPHA, 0f)
    animator2.duration=1500
    animator2.repeatCount = INFINITE
    animator2.repeatMode= ValueAnimator.RESTART
    animator2.start()
}

fun snowfallAnimation(iv: ImageView)
{
    val animator = ObjectAnimator.ofFloat(iv, View.TRANSLATION_Y, 70f)
    animator.duration=2000
    animator.repeatCount = INFINITE
    animator.repeatMode= ValueAnimator.RESTART
    animator.start()

    val animator2 = ObjectAnimator.ofFloat(iv, View.ALPHA, 0f)
    animator2.duration=2000
    animator2.repeatCount = INFINITE
    animator2.repeatMode= ValueAnimator.RESTART
    animator2.start()
}