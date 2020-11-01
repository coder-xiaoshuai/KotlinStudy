package com.example.kotlinstudy.utils

import com.tencent.mmkv.MMKV

object MMKVUtils {

    @JvmStatic
    fun putString(key: String?, value: String?) {
        notNull(key, value) {
            MMKV.defaultMMKV().encode(key, value)
        }
    }

    @JvmStatic
    fun getString(key: String?): String {
        notNull(key) {
            return MMKV.defaultMMKV().decodeString(key)
        }
        return ""
    }

    @JvmStatic
    fun putBoolean(key: String?, value: Boolean?) {
        notNull(key, value) {
            MMKV.defaultMMKV().encode(key, value!!)
        }
    }

    @JvmStatic
    fun getBoolean(key: String?): Boolean {
        notNull(key) {
            return MMKV.defaultMMKV().decodeBool(key)
        }
        return false
    }

    @JvmStatic
    fun putInt(key: String?, value: Int?) {
        notNull(key, value) {
            MMKV.defaultMMKV().encode(key, value!!)
        }
    }

    @JvmStatic
    fun getInt(key: String?): Int {
        notNull(key) {
            return MMKV.defaultMMKV().decodeInt(key)
        }
        return 0
    }

    @JvmStatic
    fun putLong(key: String?, value: Long?) {
        notNull(key, value) {
            MMKV.defaultMMKV().encode(key, value!!)
        }
    }

    @JvmStatic
    fun getLong(key: String?): Long {
        notNull(key) {
            return MMKV.defaultMMKV().decodeLong(key)
        }
        return 0
    }

    @JvmStatic
    fun putFloat(key: String?, value: Float?) {
        notNull(key, value) {
            MMKV.defaultMMKV().encode(key, value!!)
        }
    }

    @JvmStatic
    fun getFloat(key: String?): Float {
        notNull(key) {
            return MMKV.defaultMMKV().decodeFloat(key)
        }
        return 0f
    }

    @JvmStatic
    fun putDouble(key: String?, value: Double?) {
        notNull(key, value) {
            MMKV.defaultMMKV().encode(key, value!!)
        }
    }

    @JvmStatic
    fun getDouble(key: String?): Double {
        notNull(key) {
            return MMKV.defaultMMKV().decodeDouble(key)
        }
        return 0.0
    }

    private inline fun <R> notNull(vararg args: Any?, block: () -> R) {
        if (args.filterNotNull().size == args.size) {
            block()
        }
    }

}