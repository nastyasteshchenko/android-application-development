package ru.nsu.animation

import android.animation.ObjectAnimator
import android.view.View

class DampedVibrationAnimator(animatedView: View) {

    private var animator: ObjectAnimator = ObjectAnimator.ofFloat(
        animatedView, "translationX",
        60f, -60f, 40f, -40f, 20f, -20f, 5f, -5f, 0f
    ).apply { duration = 500 }

    fun start() {
        animator.start()
    }
}