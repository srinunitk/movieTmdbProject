package com.example.movie.movie_detail.data.remote.api

import com.example.movie.movie.data.remote.models.MovieDto
import com.example.movie.movie_detail.data.remote.models.MovieDetailDto
import com.example.movie.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MOVIE_ID = "movie_id"

interface MovieDetailApiService {

    @GET("${K.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}")
    suspend fun fetchMovieDetail(
        @Path(MOVIE_ID) movieId:Int,
        @Query("api_key") apiKey: String ="3b808e8aa6a692fce76e4c986503c72c", //BuildConfig.apiKey,
        @Query("append_to_response") appendToResponse: String = "credits,reviews,videos",
    ):MovieDetailDto

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchMovie(
        @Query("api_key") apiKey: String = "3b808e8aa6a692fce76e4c986503c72c",
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto

}