package com.marcelldr.thefolks.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.marcelldr.thefolks.data.DataRepository
import com.marcelldr.thefolks.data.sources.remote.RemoteDataSource
import com.marcelldr.thefolks.data.sources.remote.api.NewsApi
import com.marcelldr.thefolks.domain.repository.IDataRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
}

val networkModule = module {
    single { NewsApi(androidContext()) }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<IDataRepository> { DataRepository(get()) }
}