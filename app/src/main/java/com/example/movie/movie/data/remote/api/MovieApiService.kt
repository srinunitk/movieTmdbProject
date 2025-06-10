package com.example.movie.movie.data.remote.api

import com.example.movie.movie.data.remote.models.MovieDto
import com.example.movie.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

   // val apiKeyvalue = "3b808e8aa6a692fce76e4c986503c72c"
    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        @Query("api_key") apiKey: String = "3b808e8aa6a692fce76e4c986503c72c",//BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto

    @GET(K.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = "3b808e8aa6a692fce76e4c986503c72c",//BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto
}