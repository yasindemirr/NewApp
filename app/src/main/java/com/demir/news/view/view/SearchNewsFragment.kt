package com.demir.news.view.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.demir.news.R
import com.demir.news.databinding.FragmentSearchNewsBinding
import com.demir.news.view.view.Adepter.ArticleAdepter
import com.demir.news.view.view.UTİL.Constant.Companion.SEARCH_NEWS_TIME_DELAY
import com.demir.news.view.view.model.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment() {
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var viewModel:NewsViewModel
    private val newsAdepter= ArticleAdepter()
    private val TAG= "SearchNewsFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getBreakingNews("tr")
        binding.rvSearchNews.apply {
            this.adapter = newsAdepter
            this.layoutManager = LinearLayoutManager(activity)
        }

            newsAdepter.setOnItemClickListener {
                val bundle=Bundle().apply {
                    putSerializable("article",it)
                }
                findNavController().navigate(R.id.action_searchNewsFragment_to_articleNewsFragment,bundle)
            }




        observableLiveData()
        var job:Job?=null
        binding.etSearch.addTextChangedListener {editable->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)

                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString().trim())
                    }

                }
            }
        }

    }
    private fun observableLiveData(){
        viewModel.searchArticle.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.paginationProgressBar.visibility=View.GONE
                newsAdepter.differ.submitList(it.articles)
            }
        })

    }
}