package com.demir.news.view.view.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demir.news.view.view.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)// Herhangi bir çatışma yada daha günceli geldiinde gelenle değiştir.
    suspend fun Upsert (article: Article):Long
    @Query("SELECT*FROM article")
    fun getAllArticle():LiveData<List<Article>>//suspend yazmayışımızın ve LiveData döndürmemizin sebebi, canlı bir veri döndüreceğiz ve dödüreceğimiz verinin ne olduğnu bilmiyoruz
    @Delete
    suspend fun deleteArticles(article: Article)


}