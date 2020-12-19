package com.darkcoder.diagonal.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.darkcoder.diagonal.data.MovieModel
import com.darkcoder.diagonal.data.MovieModel.Page.ContentItems.Content
import com.darkcoder.diagonal.data.MovieRepository

class MovieViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val currentPage = MutableLiveData(DEFAULT_PAGE)
    val movies = currentPage.switchMap {
        repository.getPageDataByPageNo(it).cachedIn(viewModelScope)
    }
    fun fetchPageData(pageNoOrQuery: String) {
        currentPage.value = pageNoOrQuery
    }

    companion object {
        private const val DEFAULT_PAGE = "1"
    }
}