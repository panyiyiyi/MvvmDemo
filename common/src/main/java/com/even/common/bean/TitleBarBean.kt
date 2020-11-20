package com.even.common.bean

import android.graphics.Color
import com.even.common.R

/**
 * Created Even by 2020/10/26
 * 默认标题栏数据对象
 */
data class TitleBarBean(
    var title: String?,
    var rightText: String?,
    var rightImage: Int,
    var showRightImage: Boolean,
    var backResource: Int = Color.WHITE
) {
    constructor() : this(
        null,
        null,
        R.mipmap.ic_launcher,
        false,
        Color.WHITE
    )

    constructor(title: String) : this(
        title,
        null,
        R.mipmap.ic_launcher,
        false,
        Color.WHITE
    )

    constructor(title: String, rightText: String) : this(
        title,
        rightText,
        R.mipmap.ic_launcher,
        false,
        Color.WHITE
    )

    constructor(title: String, rightImage: Int) : this(
        title,
        null,
        rightImage,
        true,
        Color.WHITE
    )

    constructor(title: String, rightImage: Int?, backResource: Int) : this(
        title,
        null,
        R.mipmap.ic_launcher,
        false,
        backResource
    )
}