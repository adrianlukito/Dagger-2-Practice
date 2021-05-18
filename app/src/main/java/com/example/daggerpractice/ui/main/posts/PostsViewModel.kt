package com.example.daggerpractice.ui.main.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.SessionManager
import com.example.daggerpractice.models.Post
import com.example.daggerpractice.models.User
import com.example.daggerpractice.network.main.MainApi
import com.example.daggerpractice.ui.auth.AuthResource
import com.example.daggerpractice.ui.main.Resource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val mainApi: MainApi,
    private val sessionManager: SessionManager
) : ViewModel() {
    init {
        Log.d("lolo", "post view model is working... ")
    }

    private lateinit var posts: MediatorLiveData<Resource<List<Post>>>

    fun observePosts(): LiveData<Resource<List<Post>>> {
        posts = MediatorLiveData()
        posts.value = Resource.loading(null)

        sessionManager.getAuthUser().value?.data?.id?.let { userId ->
            val source = LiveDataReactiveStreams.fromPublisher(
                mainApi.getPostFromUser(userId).map {
                    Resource.success(it)
                }.onErrorReturn {
                    Resource.error("something when wrong", null)
                }.subscribeOn(Schedulers.io())
            )

            posts.addSource(source) {
                posts.value = it
                posts.removeSource(source)
            }
        }

        return posts
    }

    fun getAuthenticatedUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}