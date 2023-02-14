package com.demir.news.view.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.demir.news.R
import com.demir.news.databinding.FragmentArticleNewsBinding
import com.google.android.material.snackbar.Snackbar


class ArticleNewsFragment : Fragment() {
    private lateinit var viewModel:NewsViewModel
    private lateinit var binding: FragmentArticleNewsBinding
    private lateinit var toolbar: Toolbar
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
        toolbar= view.findViewById(R.id.toolbarArticle)
        binding.toolbarArticle.back.setOnClickListener {
            findNavController().navigateUp()
        }
      //  toolbar.navigationIcon?.setTint(Color.parseColor("#FFFFFF"))

        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
        val article= arg.article

         binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
        binding.webView.settings.apply {
            textZoom=100
            loadsImagesAutomatically=true
            displayZoomControls=false
            setSupportZoom(false)
            cacheMode=WebSettings.LOAD_DEFAULT
            blockNetworkImage=false

        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.fab.setOnClickListener{
          viewModel.upsertArticles(article)
            Snackbar.make(view,"Article save successfully",Snackbar.LENGTH_SHORT).show()




        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toolbar.navigationIcon.apply {
            findNavController().navigateUp()
        }
        return true
    }

}