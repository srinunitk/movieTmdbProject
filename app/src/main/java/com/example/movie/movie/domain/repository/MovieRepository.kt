package com.example.movie.movie.domain.repository

import com.example.movie.movie.domain.models.Movie
import com.example.movie.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchDiscoverMovie(): Flow<Response<List<Movie>>>
    fun fetchTrendingMovie(): Flow<Response<List<Movie>>>
}