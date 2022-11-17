package com.app.flobiz_assignment.room

import androidx.room.TypeConverter
import com.app.flobiz_assignment.models.QuestionResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverter {

    @TypeConverter
    fun itemToString(item: QuestionResponse.Item): String {
        val gson = Gson()
        val type: Type = object : TypeToken<QuestionResponse.Item>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun stringToItem(itemString: String): QuestionResponse.Item {
        val gson = Gson()
        val type: Type = object : TypeToken<QuestionResponse.Item>() {}.type
        return gson.fromJson(itemString, type)
    }
//
    @TypeConverter
    fun fromOwner(item: QuestionResponse.Owner): String {
        val gson = Gson()
        val type: Type = object : TypeToken<QuestionResponse.Owner>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toOwner(itemString: String): QuestionResponse.Owner {
        val gson = Gson()
        val type: Type = object : TypeToken<QuestionResponse.Owner>() {}.type
        return gson.fromJson(itemString, type)
    }
//
    @TypeConverter
    fun fromTagList(countryLang: List<String?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toTagList(countryLangString: String?): List<String>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson<List<String>>(countryLangString, type)
    }







}