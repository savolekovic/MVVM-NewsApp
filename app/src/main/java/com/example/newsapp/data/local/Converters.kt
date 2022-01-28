package com.example.newsapp.data.local

import androidx.room.TypeConverter
import com.example.newsapp.domain.entities.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name, name)
    }
}