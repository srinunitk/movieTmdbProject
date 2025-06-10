package com.example.movie.di

import com.example.movie.common.data.ApiMapper
import com.example.movie.movie.data.remote.models.MovieDto
import com.example.movie.movie.domain.models.Movie
import com.example.movie.movie_detail.data.mapper_impl.MovieDetailMapperImpl
import com.example.movie.movie_detail.data.remote.api.MovieDetailApiService
import com.example.movie.movie_detail.data.remote.models.MovieDetailDto
import com.example.movie.movie_detail.data.repo_impl.MovieDetailRepositoryImpl
import com.example.movie.movie_detail.domain.models.MovieDetail
import com.example.movie.movie_detail.domain.repository.MovieDetailRepository
import com.example.movie.utils.K
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        movieDetailApiService: MovieDetailApiService,
        mapper: ApiMapper<MovieDetail, MovieDetailDto>,
        movieMapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieDetailRepository = MovieDetailRepositoryImpl(
        movieDetailApiService = movieDetailApiService,
        apiDetailMapper = mapper,
        apiMovieMapper = movieMapper,
    )

    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<MovieDetail, MovieDetailDto> = MovieDetailMapperImpl()

   /* @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }*/

    @Provides
    @Singleton
    fun provideMovieDetailApiService(okHttpClient: OkHttpClient): MovieDetailApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieDetailApiService::class.java)
    }
}

