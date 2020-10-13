package com.bharatagri.mobile.utils

import androidx.room.TypeConverter
import com.bharatagri.mobile.service.modal.Genres
import com.bharatagri.mobile.service.modal.SpokenLanguages
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresConverter {
    @TypeConverter
    fun genresListToSpokenLanguageString(list: ArrayList<Genres?>?): String? {
        val type = object : TypeToken<List<Genres>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun stringToGenresList(string: String): ArrayList<Genres?>? {
        val type = object : TypeToken<List<Genres?>?>() {}.type
        return Gson().fromJson(string, type)
    }
}