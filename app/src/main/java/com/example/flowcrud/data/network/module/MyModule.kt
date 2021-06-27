package com.example.flowcrud.data.network.module

import com.example.flowcrud.data.network.apis.MyApis
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesMyApis(moshi: Moshi): MyApis =
        Retrofit.Builder()
            .baseUrl(MyApis.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory
                    .create(moshi)
            ).build()
            .create(MyApis::class.java)
}