package com.makeupapp.makeup.product.di

import com.makeupapp.makeup.product.domain.repository.RepositoryImplementation
import com.makeupapp.makeup.product.domain.repository.RepositoryInterface
import com.makeupapp.makeup.product.network.NetworkInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

//    provides the network interface
    @Provides
    @Singleton
    fun providesRepository(networkInterface: NetworkInterface): RepositoryInterface =
        RepositoryImplementation(networkInterface)


}