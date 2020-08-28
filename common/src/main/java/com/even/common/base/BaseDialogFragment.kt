package com.even.common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.even.common.R

/**
 * Create by Even on 2020/8/26
 * 通用弹窗布局
 */
abstract class BaseDialogFragment : DialogFragment() {
    //定义通用的标题，可以子类中实现对应的设值方法
    var titleBarView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window = dialog?.window
        window!!.decorView.setPadding(
            0,
            window.decorView.paddingTop,
            0,
            window.decorView.paddingBottom
        )
        val layoutParams = window.attributes
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = getScreenRate()
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.windowAnimations = R.style.common_dialog_fragment_anim
        window.attributes = layoutParams
        window.setBackgroundDrawable(ColorDrawable())
        this.isCancelable = isCancelDialogOnOutSide()

        if (useDefaultTitleBar()) {
            val linearLayout = LinearLayout(activity)
            linearLayout.setBackgroundColor(Color.WHITE)
            var layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            titleBarView = inflater.inflate(getTitleBarView(), container, false)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(titleBarView, layoutParams)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            val contentView = inflater.inflate(getContentView(), container, false)
            linearLayout.addView(contentView, layoutParams)

            initView(linearLayout)
            initData()
            return linearLayout
        } else {
            val contentView = inflater.inflate(getContentView(), null)
            initView(contentView)
            initData()
            return contentView
        }
    }

    //屏幕弹出
    open fun getScreenRate(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    //点击外部区域能否取消，默认可以
    open fun isCancelDialogOnOutSide(): Boolean = true

    //是否使用默认标题，默认使用
    open fun useDefaultTitleBar(): Boolean = true

    //标题布局
    abstract fun getTitleBarView(): Int

    //内容布局
    abstract fun getContentView(): Int

    //初始化View
    abstract fun initView(view: View)

    //初始化数据
    open fun initData() {}
}