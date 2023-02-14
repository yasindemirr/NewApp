package com.demir.news.view.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.demir.news.R
import com.demir.news.databinding.ActivityMainBinding
import com.demir.news.databinding.FragmentArticleNewsBinding
import com.demir.news.view.view.db.ArticleDataBase
import com.demir.news.view.view.repository.Repository

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nav= Navigation.findNavController(this,R.id.newsNavHostFragment)
        binding.bottomNav.setupWithNavController(nav)
        nav.addOnDestinationChangedListener{
                controller, destination, arguments ->
            when (destination.id) {
                R.id.breakingNewsFragment2 ->binding.bottomNav.visibility = View.VISIBLE
                R.id.savedNewsFragment2 ->binding.bottomNav.visibility = View.VISIBLE
                R.id.searchNewsFragment ->binding.bottomNav.visibility = View.VISIBLE
                else -> binding.bottomNav.visibility = View.GONE
            }
        }





    }

}