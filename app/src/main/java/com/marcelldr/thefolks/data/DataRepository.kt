package com.marcelldr.thefolks.data

import android.util.Log
import com.marcelldr.thefolks.data.sources.remote.RemoteDataSource
import com.marcelldr.thefolks.data.sources.remote.network.ApiResponse
import com.marcelldr.thefolks.domain.model.News
import com.marcelldr.thefolks.domain.repository.IDataRepository
import com.marcelldr.thefolks.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepository(private val remoteDataSource: RemoteDataSource) : IDataRepository {
    override suspend fun getNews(): Flow<Resource<List<News>>> {
        return flow {
            emit(Resource.Loading(listOf<News>()))
            when (val data = remoteDataSource.getNews()) {
                is ApiResponse.Success -> {
                    val news = data.data.map {
                        News(
                            it.source,
                            it.title,
                            it.description,
                            it.url,
                            it.urlToImage
                        )
                    }
                    emit(Resource.Success(news))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Api return nothing", listOf<News>()))
                is ApiResponse.Error -> emit(Resource.Error("Api error", listOf<News>()))
            }
        }
    }
}