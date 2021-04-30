package com.raspisanie.mai.ui.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat

/**
 * Прогрес бар с изменяющимся цветом.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class ProgressColoredBar: View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private val mAnimation: ValueAnimator? = null
    private var mColors = mutableListOf<Int>()
    private var mFirstAngle = 0f
    private var mSecondAngle = 0f
    private var mCurrentColor = 0
    private val mAnimationSet = AnimatorSet()
    private var mFirstAnimator : ValueAnimator? = null
    private var mSecondAnimator : ValueAnimator? = null
    private var mDelta = 30f

    private val mDuration = 10000L
    private val mRotateAngle = 120f

    init {
        mColors = mutableListOf(
                Color.parseColor("#0596FF"),
                Color.parseColor("#A2D8FF"),
                Color.parseColor("#C7ACFF"),
                Color.parseColor("#2579C6")
        )

        mFirstAnimator = ValueAnimator.ofFloat(mFirstAngle, mFirstAngle + mRotateAngle)
        mFirstAnimator?.duration = mDuration
        mFirstAnimator?.addUpdateListener { a ->
            mFirstAngle = a.animatedValue as Float
            mSecondAngle = a.animatedValue as Float + mDelta
            correctAngle()
            invalidate()
        }
        mFirstAnimator?.doOnEnd { mDelta = mSecondAngle - mFirstAngle }


        mSecondAnimator = ValueAnimator.ofFloat(mSecondAngle, mSecondAngle + mRotateAngle)
        mSecondAnimator?.duration = mDuration
        mSecondAnimator?.addUpdateListener { a ->
            mFirstAngle = a.animatedValue as Float
            mSecondAngle = a.animatedValue as Float + mDelta
            correctAngle()
            invalidate()
        }
        mSecondAnimator?.doOnEnd { mDelta = mSecondAngle - mFirstAngle }
        mSecondAnimator?.doOnEnd {
            mCurrentColor++
            if (mCurrentColor == mColors.size) mCurrentColor = 0
        }

        mAnimationSet.play(mSecondAnimator).after(mFirstAnimator)
        mAnimationSet.doOnEnd { mAnimationSet.start() }
        mAnimationSet.start()
    }

    /**
     * Приводим углы к 360.
     */
    private fun correctAngle() {
        mFirstAngle %= 360
        mSecondAngle %= 360
    }

    /**
     * Установка цветов для прогресбара.
     * @param colors список цветов.
     */
    fun setColors(colors: MutableList<Int>) {
        if (colors.size > 0) {
            mColors = colors
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val mPaint = Paint()
        val h = measuredHeight
        val w = measuredWidth
        mPaint.flags = Paint.ANTI_ALIAS_FLAG

        mPaint.color = mColors[mCurrentColor]
        canvas!!.drawArc(w / 2 - h / 2.toFloat(), 0f, w / 2 + h / 2.toFloat(), h
                .toFloat(), mFirstAngle, mSecondAngle, true, mPaint)

           /* val angle: Float = data.get(i) * 360 / dataSum
            paint.setColor(colors.get(i))
            canvas!!.drawCircle((Math.cos(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h / 20)).toFloat() + w / 2,
                    (Math.sin(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h / 20)).toFloat() + h / 2,
                    h / 20.toFloat(), paint)
            lastAngle += angle*/

        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawCircle(w / 2.toFloat(), h / 2.toFloat(), h / 2 - h / 10.toFloat(), mPaint)
    }
}