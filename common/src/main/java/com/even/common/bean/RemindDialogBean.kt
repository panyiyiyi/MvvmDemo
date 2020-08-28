package com.even.common.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Create by Even on 2020/8/28
 * 提醒弹框数据类对象
 */
data class RemindDialogBean(
    val title: String?,
    val msg: String,
    val btnLeft: String?,
    val btnRight: String?,
    val titleGravity: Int?,
    val msgGravity: Int?,
    val msgColor: Int?,
    val btnLeftColor: Int?,
    val btnRightColor: Int?,
    val canCancelOutSide: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte()
    ) {
    }

    //点击外部区域不能取消
    constructor(title: String, msg: String, btnLeft: String, btnRight: String) : this(
        title,
        msg,
        btnLeft,
        btnRight,
        null,
        null,
        null,
        null,
        null,
        false
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(msg)
        parcel.writeString(btnLeft)
        parcel.writeString(btnRight)
        parcel.writeValue(titleGravity)
        parcel.writeValue(msgGravity)
        parcel.writeValue(msgColor)
        parcel.writeValue(btnLeftColor)
        parcel.writeValue(btnRightColor)
        parcel.writeByte(if (canCancelOutSide) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RemindDialogBean> {
        override fun createFromParcel(parcel: Parcel): RemindDialogBean {
            return RemindDialogBean(parcel)
        }

        override fun newArray(size: Int): Array<RemindDialogBean?> {
            return arrayOfNulls(size)
        }
    }
}