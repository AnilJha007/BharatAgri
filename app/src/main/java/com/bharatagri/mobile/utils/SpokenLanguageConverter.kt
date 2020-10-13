package com.bharatagri.mobile.utils

import androidx.room.TypeConverter
import com.bharatagri.mobile.service.modal.SpokenLanguages
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpokenLanguageConverter {
    @TypeConverter
    fun spokenLanguageListToString(list: ArrayList<SpokenLanguages?>?): String? {
        val type = object : TypeToken<List<SpokenLanguages>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun stringToSpokenLanguageList(string: String): ArrayList<SpokenLanguages?>? {
        val type = object : TypeToken<List<SpokenLanguages?>?>() {}.type
        return Gson().fromJson(string, type)
    }
}