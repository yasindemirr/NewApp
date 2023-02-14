package com.demir.news.view.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.demir.news.R
import com.demir.news.databinding.FragmentSavedNewsBinding
import com.demir.news.databinding.FragmentSearchNewsBinding
import com.demir.news.view.view.Adepter.ArticleAdepter
import com.demir.news.view.view.db.ArticleDataBase
import com.demir.news.view.view.model.Article
import com.demir.news.view.view.repository.Repository
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment() : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var toolbar: Toolbar
    private val newsAdepter = ArticleAdepter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar= view.findViewById(R.id.toolBarSave)
        toolbar.title="Saved News"
        binding.toolBarSave.back.visibility=View.GONE
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
        binding.rvSavedNews.apply {
            this.adapter = newsAdepter
            this.layoutManager = LinearLayoutManager(activity)

        }
        newsAdepter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment2_to_articleNewsFragment,bundle)
        }

        val itemTouchHelperCallBack=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return true

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
               val article= newsAdepter.differ.currentList[position]
                viewModel.deleteArticles(article)
                Snackbar.make(view,"Deleted article successfully",Snackbar.LENGTH_SHORT).apply {
                    setAction("undo"){
                        viewModel.upsertArticles(article)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
        observeSaveData()


    }
    fun observeSaveData(){
       viewModel.getSavedArticle().observe(viewLifecycleOwner, Observer {
            it?.let {
               newsAdepter.differ.submitList(it)

            }

        })



    }

}