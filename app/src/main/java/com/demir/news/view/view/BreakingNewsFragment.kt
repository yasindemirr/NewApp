package com.demir.news.view.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.demir.news.R
import com.demir.news.databinding.FragmentBreakingNewsBinding
import com.demir.news.view.view.Adepter.ArticleAdepter
import com.demir.news.view.view.db.ArticleDataBase
import com.demir.news.view.view.repository.Repository


class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var viewModel: NewsViewModel
    private val newsAdepter=ArticleAdepter()
    private val TAG= "BreakingNewsFragment"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBreakingNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getBreakingNews("tr")
        binding.rvBreakingNews.apply {
            this.adapter=newsAdepter
            this.layoutManager=LinearLayoutManager(activity)
        }

        newsAdepter.setOnItemClickListener {
           val bundle=Bundle().apply {
               putSerializable("article",it)
           }
            findNavController().navigate(R.id.action_breakingNewsFragment2_to_articleNewsFragment,bundle)
        }



        observableLiveData()

    }
    private fun observableLiveData(){
        viewModel.newsArticles.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.paginationProgressBar.visibility=View.GONE
                newsAdepter.differ.submitList(it.articles)
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.paginationProgressBar.visibility=View.VISIBLE

                }

            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    
                    Log.e(TAG,"AN ERROR OCURED")

            }

            }

        })



    }




}