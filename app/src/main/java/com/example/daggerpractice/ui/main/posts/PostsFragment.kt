package com.example.daggerpractice.ui.main.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daggerpractice.R
import com.example.daggerpractice.ui.auth.AuthResource
import com.example.daggerpractice.ui.main.Resource
import com.example.daggerpractice.util.VerticalSpacingItemDecoration
import com.example.daggerpractice.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostsFragment: DaggerFragment() {

    private lateinit var viewModel: PostsViewModel

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var adapter: PostsRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(PostsViewModel::class.java)

        initRecyclerView()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)
        viewModel.observePosts().observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    Log.d("lolo", "LOADING...")
                }
                Resource.Status.SUCCESS -> {
                    Log.d("lolo", "SUCCESS")
                    it.data?.let { it1 -> adapter.setPosts(it1) }
                }
                Resource.Status.ERROR -> {
                    Log.d("lolo", "ERROR ${it.message}")
                }
            }
        })
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(VerticalSpacingItemDecoration(15))
    }
}