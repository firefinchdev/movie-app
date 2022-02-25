package com.android.movieapp.service

import com.android.movieapp.utils.json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(".")
    suspend fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String,
        @Query("page") page: Int = 1
    ): MoviesResponse

    @GET(".")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") id: String
    ): MovieResponse


    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        fun create(
            baseUrl: String = "https://www.omdbapi.com/",
            interceptors: List<Interceptor> = listOf(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            ),
            convertorFactory: Converter.Factory = json.asConverterFactory("application/json".toMediaType())
        ): MovieService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(
                    OkHttpClient.Builder()
                        .apply { interceptors.forEach { addInterceptor(it) } }
                        .build()
                )
                .addConverterFactory(convertorFactory)
                .build()
                .create(MovieService::class.java)
        }
    }
}