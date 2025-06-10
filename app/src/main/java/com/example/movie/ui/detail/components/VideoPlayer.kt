package com.example.movie.ui.detail.components

import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }

    DisposableEffect(videoUrl) {
        val player = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
            playWhenReady = true
        }
        exoPlayer = player
        onDispose {
            player.release()
            exoPlayer = null
        }
    }

    exoPlayer?.let { player ->
        AndroidView(
            modifier = modifier,
            factory = {
                PlayerView(it).apply {
                    this.player = player
                    useController = true
                }
            }
        )
    }
}

