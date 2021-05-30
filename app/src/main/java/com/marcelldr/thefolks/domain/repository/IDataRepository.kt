package com.marcelldr.thefolks.domain.repository

import com.marcelldr.thefolks.domain.model.News
import com.marcelldr.thefolks.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    suspend fun getNews(): Flow<Resource<List<News>>>
}