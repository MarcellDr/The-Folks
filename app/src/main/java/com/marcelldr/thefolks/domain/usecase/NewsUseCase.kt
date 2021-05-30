package com.marcelldr.thefolks.domain.usecase

import com.marcelldr.thefolks.domain.model.News
import com.marcelldr.thefolks.vo.Resource
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    suspend fun getNews(): Flow<Resource<List<News>>>
}