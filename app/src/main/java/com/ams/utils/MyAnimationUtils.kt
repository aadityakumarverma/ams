package com.ams.utils

import android.view.View
import android.widget.ImageView
import kotlin.collections.forEachIndexed
import kotlin.collections.isEmpty

object MyAnimationUtils {


    private val animatedPositions = mutableSetOf<Int>()
    fun animateItem(view: View, position: Int, delayPerItem: Long = 150L, duration: Long = 500L) {
        if (!animatedPositions.contains(position)) {
            view.alpha = 0f
            view.visibility = View.VISIBLE
            view.animate()
                .alpha(1f)
                .setStartDelay(position * delayPerItem)
                .setDuration(duration)
                .start()
            animatedPositions.add(position)
        }
    }
    fun clearAnimatedPositions() {
        animatedPositions.clear()
    }



    fun animateViews(
        vararg views: View, startDelayMs: Long = 0, staggerDelayMs: Long = 100, durationMs: Long = 300,
        initialAlpha: Float = 0f, targetAlpha: Float = 1f, onAnimationEnd: (() -> Unit)? = null) {
        if (views.isEmpty()) {
            onAnimationEnd?.invoke()
            return
        }
        var completedAnimations = 0
        val totalAnimations = views.size
        views.forEachIndexed { index, view ->
            view.alpha = initialAlpha
            view.visibility = View.VISIBLE
            view.animate()
                .alpha(targetAlpha)
                .setStartDelay(startDelayMs + index * staggerDelayMs)
                .setDuration(durationMs)
                .withEndAction {
                    completedAnimations++
                    if (completedAnimations == totalAnimations) {
                        onAnimationEnd?.invoke()
                    }
                }.start()
        }
    }


    fun View.fadeIn(duration: Long = 300) {
        this.alpha = 0f
        this.visibility = View.VISIBLE
        this.animate()
            .alpha(1f)
            .setDuration(duration)
            .start()
    }

    fun View.fadeOut2(duration: Long = 200, endAction: (() -> Unit)? = null) {
        this.animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction {
                this.visibility = View.GONE
                endAction?.invoke()
            }
            .start()
    }

    fun View.slideInFromRight(duration: Long = 300) {
        this.translationX = 200f
        this.alpha = 0f
        this.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(duration)
            .start()
    }

    fun View.slideInFromLeft(duration: Long = 300) {
        this.translationX = -200f
        this.alpha = 0f
        this.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(duration)
            .start()
    }

    fun ImageView.fadeImageChange(newImageRes: Int, duration: Long = 300) {
        this.animate()
            .alpha(0f)
            .setDuration(duration / 2)
            .withEndAction {
                this.setImageResource(newImageRes)
                this.animate()
                    .alpha(1f)
                    .setDuration(duration / 2)
                    .start()
            }
            .start()
    }
    fun View.fadeIn(duration: Long = 300, end: (() -> Unit)? = null) {
        alpha = 0f
        visibility = View.VISIBLE
        animate().alpha(1f).setDuration(duration).withEndAction { end?.invoke() }
    }

    fun View.fadeOut(duration: Long = 300, end: (() -> Unit)? = null) {
        animate().alpha(0f).setDuration(duration).withEndAction {
            visibility = View.GONE
            end?.invoke()
        }
    }

    fun View.shrinkTo(duration: Long = 1000, end: (() -> Unit)? = null) {
        animate().scaleX(0f).setDuration(duration).withEndAction {
            visibility = View.GONE
            end?.invoke()
        }
    }

}