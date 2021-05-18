package com.example.daggerpractice.ui.main.posts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerpractice.R
import com.example.daggerpractice.models.Post
import kotlinx.android.synthetic.main.posts_list_item.view.*

class PostsRecyclerAdapter: RecyclerView.Adapter<PostsRecyclerAdapter.PostViewHolder>() {
    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsRecyclerAdapter.PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.posts_list_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsRecyclerAdapter.PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            itemView.run {
                title.text = post.title
            }
        }
    }
}