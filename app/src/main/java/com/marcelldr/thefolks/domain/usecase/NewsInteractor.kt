package com.marcelldr.thefolks.domain.usecase

import android.util.Log
import com.marcelldr.thefolks.domain.model.News
import com.marcelldr.thefolks.domain.repository.IDataRepository
import com.marcelldr.thefolks.vo.Resource
import kotlinx.coroutines.flow.Flow

class NewsInteractor(private val dataRepository: IDataRepository) : NewsUseCase {
    override suspend fun getNews(): Flow<Resource<List<News>>> {
        return dataRepository.getNews()
    }
}