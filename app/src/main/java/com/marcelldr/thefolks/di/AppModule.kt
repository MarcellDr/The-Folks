package com.marcelldr.thefolks.di

import com.marcelldr.thefolks.domain.usecase.NewsInteractor
import com.marcelldr.thefolks.domain.usecase.NewsUseCase
import com.marcelldr.thefolks.presentation.home.dashboard.NewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}