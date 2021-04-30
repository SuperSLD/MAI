package com.raspisanie.mai.ui.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import com.smarteist.autoimageslider.IndicatorView.animation.type.ColorAnimation
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

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

    private var mColors = mutableListOf<Int>()
    private var mFirstAngle = 0f
    private var mSecondAngle = 30f
    private var mCurrentColorIndex = 0
    private var mCurrentColor = 0
    private var mFirstAnimator : ValueAnimator? = null
    private var mSecondAnimator : ValueAnimator? = null
    private var mColorAnimator: ValueAnimator? = null
    private var mLastFirstAngle = mFirstAngle
    private var mLastSecondAngle = mSecondAngle

    private val mWithPart = 0.13f
    private val mDuration = 1000L
    private val mDurationColor = 1000L
    private val mRotateAngle = 220f

    init {
        mColors = mutableListOf(
                Color.parseColor("#EA4335"),
                Color.parseColor("#FBBC05"),
                Color.parseColor("#34A853"),
                Color.parseColor("#4285F4")
        )

        mCurrentColor = mColors[0]

        mFirstAnimator = ValueAnimator.ofFloat(0f, mRotateAngle)
        with(mFirstAnimator!!) {
            duration = mDuration
            repeatCount = 0
            interpolator = LinearInterpolator()
            addUpdateListener { a ->
                mFirstAngle = mLastFirstAngle + a.animatedValue as Float
                mSecondAngle = mLastSecondAngle + a.animatedValue as Float * 2
                correctAngle()
                invalidate()
            }
            doOnEnd {
                mLastFirstAngle = mFirstAngle
                mLastSecondAngle = mSecondAngle
                mSecondAnimator?.start()
            }
        }

        mSecondAnimator = ValueAnimator.ofFloat(0f, mRotateAngle)
        with(mSecondAnimator!!) {
            duration = mDuration
            repeatCount = 0
            interpolator = LinearInterpolator()
            addUpdateListener { a ->
                mFirstAngle = mLastFirstAngle + a.animatedValue as Float * 2
                mSecondAngle = mLastSecondAngle + a.animatedValue as Float
                correctAngle()
                invalidate()
            }
            doOnEnd {
                mCurrentColorIndex++
                if (mCurrentColorIndex == mColors.size) mCurrentColorIndex = 0

                mColorAnimator = ValueAnimator.ofArgb(
                        mColors[if (mCurrentColorIndex - 1 < 0) mColors.size - 1 else mCurrentColorIndex - 1],
                        mColors[mCurrentColorIndex]
                )
                with(mColorAnimator!!) {
                    duration = mDurationColor
                    repeatCount = 0
                    addUpdateListener { a -> mCurrentColor = a.animatedValue as Int }
                    start()
                }

                mLastFirstAngle = mFirstAngle
                mLastSecondAngle = mSecondAngle
                mFirstAnimator?.start()
            }
        }

        mFirstAnimator?.start()
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

        mPaint.color = mCurrentColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = h * mWithPart
        canvas!!.drawArc(
                w / 2 - h / 2.toFloat() + h*mWithPart/2,
                0f  + h*mWithPart/2,
                w / 2 + h / 2.toFloat() - h*mWithPart/2,
                h.toFloat() - h*mWithPart/2,
                mFirstAngle, secondAngleToDelta() - mFirstAngle,
                false,
                mPaint
        )

        var lastAngle = 0f
        mPaint.style = Paint.Style.FILL
        for (angle in mutableListOf(mFirstAngle, secondAngleToDelta() - mFirstAngle)) {
            canvas.drawCircle((cos(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h*mWithPart/2)).toFloat() + w / 2,
                    (sin(Math.toRadians(lastAngle + angle.toDouble())) * (h / 2 - h*mWithPart/2)).toFloat() + h / 2,
                    h*mWithPart/2, mPaint)
            lastAngle += angle
        }
    }

    /**
     * Приводим угол к виду из которого с ним
     * удобнее работать для прибавления углов.
     *
     * @return mSecondAngle в интервале от 0 дл 360 если
     * он больше первого угла и +360 если он меньше первого угла.
     */
    private fun secondAngleToDelta() = if (mSecondAngle < mFirstAngle) mSecondAngle + 360 else mSecondAngle
}