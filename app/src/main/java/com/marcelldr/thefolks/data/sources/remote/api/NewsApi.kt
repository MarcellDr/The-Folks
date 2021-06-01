package com.marcelldr.thefolks.data.sources.remote.api

import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.marcelldr.thefolks.BuildConfig
import com.marcelldr.thefolks.data.sources.remote.network.ApiResponse
import com.marcelldr.thefolks.data.sources.remote.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NewsApi(context: Context) {
    companion object {
        private const val API_KEY = BuildConfig.NEWSAPI_KEY
        private const val BASE_URL_NEWS = "https://newsapi.org/v2/everything"
    }

    init {
        AndroidNetworking.initialize(context)
    }

    suspend fun getNews(page: Int = 1): List<NewsResponse> {
        val list = mutableListOf<NewsResponse>()
        withContext(Dispatchers.IO) {
            val request = AndroidNetworking.get(BASE_URL_NEWS)
                .addQueryParameter("q", "kartu")
                .addQueryParameter("language", "id")
                .addQueryParameter("page", page.toString())
                .addQueryParameter("excludeDomains", "pinterpoin.com")
                .addQueryParameter("apiKey", API_KEY)
                .setPriority(Priority.LOW)
                .build()
            val response = request.executeForJSONObject()
            if (response.isSuccess) {
                val json: JSONObject = response.result as JSONObject
                list.addAll(jsonParse(json))
                Log.i("Success", "Success")
            } else {
                Log.i("Error", "Error")
                throw response.error
            }
        }
        return list
    }

    private fun jsonParse(json: JSONObject): List<NewsResponse> {
        val list = mutableListOf<NewsResponse>()
        val array = json.getJSONArray("articles")
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            list.add(
                NewsResponse(
                    source = item.getJSONObject("source").getString("name"),
                    title = item.getString("title"),
                    description = item.getString("description"),
                    url = item.getString("url"),
                    urlToImage = item.getString("urlToImage")
                )
            )
        }
        return list
    }
}