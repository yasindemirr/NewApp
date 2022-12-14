package com.demir.news.view.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.demir.news.databinding.FragmentArticleNewsBinding
import com.google.android.material.snackbar.Snackbar


class ArticleNewsFragment : Fragment() {
    private lateinit var viewModel:NewsViewModel
    private lateinit var binding: FragmentArticleNewsBinding
    val arg:ArticleNewsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentArticleNewsBinding.inflate(inflater,container,false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
        val article= arg.article
         binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener{
            viewModel.saveArticles(article)
            Snackbar.make(view,"Article save successfully",Snackbar.LENGTH_SHORT).show()

        }






    }




}