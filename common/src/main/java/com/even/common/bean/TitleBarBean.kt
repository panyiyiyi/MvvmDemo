package com.even.common.bean

import android.graphics.Color
import com.even.common.R
import com.even.commonrv.utils.DisplayUtil

/**
 * Created Even by 2020/10/26
 * 默认标题栏数据对象
 */
data class TitleBarBean(
    var leftImage: Int = R.mipmap.common_ic_back,
    var title: String? = "",
    var titleTextSize: Int = DisplayUtil.sp2px(17f).toInt(),
    var titleTextColor: Int = Color.WHITE,
    var rightText: String? = "",
    var rightTextSize: Int = DisplayUtil.sp2px(14f).toInt(),
    var rightTextColor: Int = Color.WHITE,
    var rightImage: Int = R.mipmap.common_ic_back,
    var showRightImage: Boolean = false,
    var bgResource: Int = Color.WHITE
)