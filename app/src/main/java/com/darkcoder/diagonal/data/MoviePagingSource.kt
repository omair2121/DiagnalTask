package com.darkcoder.diagonal.data

import android.content.Context
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.darkcoder.diagonal.data.MovieModel.Page.ContentItems.Content
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

class MoviePagingSource constructor(
    private val context: Context,
    private val query: String
) : PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        val position = params.key ?: 1
        return try {
            var movies: List<Content> = getDataFromAsset(position)!!.page.contentItems.content
            var filtered: List<Content>? = null
            if (!query.isDigitsOnly()) {
                filtered = movies.filter { it.name.contains(query, true) }
            }
            LoadResult.Page(
                data = if (!query.isDigitsOnly()) filtered!! else movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.isNotEmpty()) position + 1 else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

//    class SweetSearchDataSourceFactory() :
//        DataSource.Factory<Int, MovieModel>() {
//
//        var query = ""
//
//        override fun create(): DataSource<Int, MovieModel> {
//            return dao.searchSweets(query).map { /*MovieModel()*/ }.create()
//        }
//
//        fun search(text: String) {
//            query = text
//        }
//    }

    private fun getDataFromAsset(no: Int): MovieModel? {
        var json: String? = null
        json = try {
            Log.d("My", "getPageData: loading page $no")
            val `is`: InputStream = context.assets.open("page$no.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return Gson().fromJson(json, MovieModel::class.java)
    }
}