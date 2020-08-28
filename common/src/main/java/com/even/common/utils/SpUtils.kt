package com.even.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SpUtils {
    /**
     * 获取
     */
    fun get(key: String, defaultValue: Any): Any? {
        return when (defaultValue) {
            is String -> sp.getString(key, defaultValue)
            is Int -> sp.getInt(key, defaultValue)
            is Boolean -> sp.getBoolean(key, defaultValue)
            is Float -> sp.getFloat(key, defaultValue)
            is Long -> sp.getLong(key, defaultValue)
            is Set<*> -> sp.getStringSet(key, defaultValue as MutableSet<String>?)
            else -> defaultValue
        }
    }

    /**
     * 存储
     */
    @SuppressLint("CommitPrefEdits")
    fun put(key: String, value: Any) {
        val edit = sp.edit()
        when (value) {
            is String -> edit.putString(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
            is Int -> edit.putInt(key, value)
            is Long -> edit.putLong(key, value)
            is Set<*> -> edit.putStringSet(key, value as MutableSet<String>?)
        }
        edit.apply()
    }

    /**
     * 移除指定key数据
     */
    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    /**
     * 清楚指定Sp数据
     */
    fun clear() {
        sp.edit().clear().apply()
    }


    companion object {
        private var spName = "Even"
        private lateinit var sp: SharedPreferences
        private var instance: SpUtils? = null
        fun getInstance(): SpUtils {
            return getInstance(spName)
        }

        fun getInstance(spName: String): SpUtils {
            if (null == instance) {
                synchronized(SpUtils::class) {
                    if (null == instance) {
                        instance = SpUtils()
                    }
                }
            }
            sp = ApplicationUtils.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE)
            return instance!!
        }

        /**
         * 初始化Application中调用，用来设置默认得sp名字
         */
        fun init(spName: String) {
            this.spName = spName
        }
    }
}