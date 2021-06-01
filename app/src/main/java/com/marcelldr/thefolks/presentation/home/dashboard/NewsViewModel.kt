package com.marcelldr.thefolks.presentation.home.dashboard

import androidx.lifecycle.ViewModel
import com.marcelldr.thefolks.domain.model.News
import com.marcelldr.thefolks.domain.usecase.NewsUseCase
import com.marcelldr.thefolks.vo.Resource
import kotlinx.coroutines.flow.Flow

class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    suspend fun getNews(): Flow<Resource<List<News>>> {
        return newsUseCase.getNews()
    }
}