package com.example.movie.ui.detail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movie.ui.components.LoadingView
import com.example.movie.ui.detail.components.DetailBodyContent
import com.example.movie.ui.detail.components.DetailTopContent
import com.example.movie.ui.detail.components.VideoPlayer

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailViewMode: DetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit
) {
    val state by movieDetailViewMode.detailState.collectAsStateWithLifecycle()
    var showVideoPlayer by remember { mutableStateOf(false) }
    var videoUrl by remember { mutableStateOf("https://www.youtube.com/watch?v=YbJOTdZBX1g") }
    Box(modifier = modifier.fillMaxWidth()) {
        if (showVideoPlayer && videoUrl.isNotEmpty()) {
            VideoPlayer(
                videoUrl = videoUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
          //  ExoMediaPlayer(videoUrl=videoUrl,modifier=modifier)
            IconButton(onClick = { showVideoPlayer = false }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Close Video")
            }
        } else {
            AnimatedVisibility(
                state.error != null,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Text(
                    state.error ?: "unknown",
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 2
                )
            }
            AnimatedVisibility(visible = !state.isLoading && state.error == null) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val boxHeight = maxHeight
                    val topItemHeight = boxHeight * .4f
                    val bodyItemHeight = boxHeight * .6f
                    state.movieDetail?.let { movieDetail ->
                        DetailTopContent(
                            movieDetail = movieDetail,
                            modifier = Modifier
                                .height(topItemHeight)
                                .align(Alignment.TopCenter),
                            onWatchTrailer = { url ->
                                videoUrl = url
                                showVideoPlayer = true
                            }
                        )
                        DetailBodyContent(
                            movieDetail = movieDetail,
                            movies = state.movies,
                            isMovieLoading = state.isMovieLoading,
                            fetchMovies = movieDetailViewMode::fetchMovie,
                            onMovieClick = onMovieClick,
                            onActorClick = onActorClick,
                            onWatchVideo = { url ->
                                videoUrl = url
                                showVideoPlayer = true
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .height(bodyItemHeight)
                        )
                    }
                }
            }
            IconButton(onClick = onNavigateUp, modifier = Modifier.align(Alignment.TopStart)) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
            }
        }
    }
    LoadingView(isLoading = state.isLoading)
}

