package com.demir.news.view.view.db

import androidx.room.TypeConverter
import com.demir.news.view.view.model.Source
//bu sınıfı kullanmamızın sebebi, Article modelinie gittiğimizde source değişkeni Source sınıfını döndürüyor
//ancak bu değişkenden database e ne gelecek belli değil.Bu yüzden converters(dönüştürücü) ile bu sınıfa
//giderken ve bu sınıftan gelirken bize name döndürmesini istiyoruz.
class Converters {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}