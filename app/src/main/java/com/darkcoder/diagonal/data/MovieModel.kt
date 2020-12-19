package com.darkcoder.diagonal.data

import com.google.gson.annotations.SerializedName

data class MovieModel(val page: Page) {
    data class Page(
        @SerializedName("content-items")
        val contentItems: ContentItems,
        @SerializedName("page-num")
        val pageNum: String,
        @SerializedName("page-size")
        val pageSize: String,
        val title: String,
        @SerializedName("total-content-items")
        val totalContentItems: String
    ) {
        data class ContentItems(
            val content: List<Content>
        ) {
            data class Content(
                val name: String,
                @SerializedName("poster-image")
                val posterImage: String
            )
        }
    }
}