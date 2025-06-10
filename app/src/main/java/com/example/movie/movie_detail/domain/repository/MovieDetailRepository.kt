package com.example.movie.movie_detail.domain.repository

import com.example.movie.movie.domain.models.Movie
import com.example.movie.movie_detail.domain.models.MovieDetail
import com.example.movie.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>>
    fun fetchMovie(): Flow<Response<List<Movie>>>
}