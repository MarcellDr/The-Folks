package com.marcelldr.thefolks.data.sources.remote

import android.util.Log
import com.marcelldr.thefolks.data.sources.remote.api.NewsApi
import com.marcelldr.thefolks.data.sources.remote.network.ApiResponse
import com.marcelldr.thefolks.data.sources.remote.response.NewsResponse

class RemoteDataSource(private val newsApi: NewsApi) {
    suspend fun getNews(page: Int = 1): ApiResponse<List<NewsResponse>> {
        return try {
            val news = newsApi.getNews(page)
            if (news.isNotEmpty()) {
                ApiResponse.Success(news)
            } else {
                ApiResponse.Empty
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }
}