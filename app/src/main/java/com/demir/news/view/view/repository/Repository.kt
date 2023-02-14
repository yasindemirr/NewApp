package com.demir.news.view.view.repository


import android.app.Application
import com.demir.news.view.view.db.ArticleDao
import com.demir.news.view.view.db.ArticleDataBase
import com.demir.news.view.view.model.Article

class Repository(private  val dao :ArticleDao){

    suspend fun upsertArticles(article: Article)=dao.Upsert(article)
    suspend fun deleteArticles(article: Article)=dao.deleteArticles(article)
    fun getSaveArticles()=dao.getAllArticle()
}