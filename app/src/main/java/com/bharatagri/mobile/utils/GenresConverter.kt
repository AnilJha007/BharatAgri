package com.bharatagri.mobile.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresConverter {
    @TypeConverter
    fun integerListToString(list: ArrayList<Int?>?): String? {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun stringToIntegerList(string: String): ArrayList<Int?>? {
        val type = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(string, type)
    }
}