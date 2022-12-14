package com.demir.news.view.view.Adepter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demir.news.databinding.ItemArticleBinding
import com.demir.news.view.view.BreakingNewsFragmentDirections
import com.demir.news.view.view.SavedNewsFragmentDirections
import com.demir.news.view.view.SearchNewsFragmentDirections
import com.demir.news.view.view.model.Article


 class ArticleAdepter:RecyclerView.Adapter<ArticleAdepter.ArticleViewHolder>() {
     class ArticleViewHolder(var binding:ItemArticleBinding) :
         RecyclerView.ViewHolder(binding.root)

     private val differCallback = object : DiffUtil.ItemCallback<Article>() {
         override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
             return oldItem.url == newItem.url
         }

         override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
             return oldItem == newItem

         }


     }
     val differ = AsyncListDiffer(this, differCallback)


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
         val view = ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         return ArticleViewHolder(view)
     }
     private var onItemClickListener: ((Article) -> Unit)? = null

     override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
         val article = differ.currentList.get(position)
         holder.itemView.apply {
             Glide.with(this).load(article.urlToImage).into(holder.binding.ivArticleImage)
         }
         holder.binding.apply {
             tvSource.text = article.source.name
             tvTitle.text = article.title
             tvDescription.text = article.description
             tvPublishedAt.text = article.publishedAt
         }
         holder.itemView.setOnClickListener {
             onItemClickListener?.let { it(article) }
         }

         }
     fun setOnItemClickListener(listener: (Article) -> Unit) {
         onItemClickListener = listener
     }

     override fun getItemCount(): Int {
         return differ.currentList.size
     }



 }


