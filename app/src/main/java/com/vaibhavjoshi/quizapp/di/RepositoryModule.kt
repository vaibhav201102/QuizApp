package com.vaibhavjoshi.quizapp.di

import com.vaibhavjoshi.quizapp.data.repository.quizRepository
import com.vaibhavjoshi.quizapp.data.repository.quizRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesloginrepositoryimpl(repository: quizRepositoryImpl) : quizRepository

}