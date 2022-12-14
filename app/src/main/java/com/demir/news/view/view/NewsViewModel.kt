package com.demir.news.view.view

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demir.news.view.view.api.ApiServive
import com.demir.news.view.view.db.ArticleDataBase
import com.demir.news.view.view.model.Article
import com.demir.news.view.view.model.NewsResponse
import com.demir.news.view.view.repository.Repository
import kotlinx.coroutines.launch

//val db= ArticleDataBase(?).getArticleDao diyerek verileri çekmemiz gerekir anca soru işaretli
//bizden context soruyor. Eğer ki NewsModel(db)
class NewsViewModel(app:Application): AndroidViewModel(app) {

private val apiService= ApiServive

   val repository:Repository
    init {
       val dao= ArticleDataBase.invoke(app).getArticleDao()
        repository= Repository(dao)
    }

    //val db= ArticleDataBase(app).getArticleDao()
    var breakingnews=1
    val newsArticles= MutableLiveData<NewsResponse>()
    val error= MutableLiveData<Boolean>()
    val loading= MutableLiveData<Boolean>()
    var searchPackage= 1
    val searchArticle= MutableLiveData<NewsResponse>()

    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        loading.value=true
        val response= apiService.getBreakingNews(countryCode,breakingnews)
        if (response.isSuccessful){
            response.body()?.let {
                showsResponses(it)
            }
        }else{
            response.body()?.let {
                loading.value=true
                error.value=false
            }
        }
    }
    fun searchNews(countryCode: String)=viewModelScope.launch {
        loading.value=true
        val response=apiService.searchNews(countryCode,searchPackage)
        if (response.isSuccessful){
            response.body()?.let {
                showsSearchResponse(it)
            }
        }else{
            response.body()?.let {
                loading.value=true
                error.value=false
            }
        }
    }
    private fun showsSearchResponse(newsResponse: NewsResponse){
        searchArticle.value=newsResponse
        error.value=false
        loading.value=false
    }
    private fun showsResponses(newsResponse: NewsResponse){
        newsArticles.value=newsResponse
        error.value=false
        loading.value=false


    }

    fun saveArticles(article: Article)=viewModelScope.launch {
        repository.saveArticles(article)
        //db.Upsert(article)
    }
    fun getSavedArticle()=
        repository.getSaveArticles()
       // db.getAllArticle()
    fun deleteArticles(article: Article)=viewModelScope.launch {
        repository.deleteArticles(article)
        //db.deleteArticles(article)
    }






}