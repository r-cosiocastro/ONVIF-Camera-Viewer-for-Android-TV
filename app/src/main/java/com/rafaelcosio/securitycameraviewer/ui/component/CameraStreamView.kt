package com.rafaelcosio.securitycameraviewer.ui.component

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.upstream.DefaultAllocator
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.rafaelcosio.securitycameraviewer.R

@OptIn(UnstableApi::class)
@Composable
fun CameraStreamView(
    streamUrl: String,
    isRtsp: Boolean = true
) {
    val context = LocalContext.current
    val loadControl = DefaultLoadControl.Builder()
        .setAllocator(DefaultAllocator(true, 64 * 1024))
        .setBufferDurationsMs(
            30_000, // 30 segundos
            60_000, //60 segundos
            5_000,  // 5 segundos
            2_500   // 2.5 segundos
        )
        .setTargetBufferBytes(DefaultLoadControl.DEFAULT_TARGET_BUFFER_BYTES * 5)
        .setPrioritizeTimeOverSizeThresholds(true)
        .build()

    if (isRtsp) {
        val exoPlayer = remember(streamUrl) {
            ExoPlayer.Builder(context)
                .setLoadControl(loadControl)
                .build().apply {
                setMediaItem(MediaItem.fromUri(streamUrl))
                    addListener(object : Player.Listener {
                        override fun onPlayerError(error: PlaybackException) {
                            Log.e("MyExoPlayer", "Player Error: ${error.message}", error)
                        }

                        override fun onRenderedFirstFrame() {
                            Log.d("MyExoPlayer", "Rendered first frame")
                            val videoFormat = this@apply.videoFormat
                            if (videoFormat != null) {
                                Log.d("MyExoPlayer", "Video Format: MIME Type: ${videoFormat.sampleMimeType}, Codec Name (from format): ${videoFormat.codecs}")
                            }
                        }

                        override fun onPlaybackStateChanged(playbackState: Int) {
                            Log.d("MyExoPlayer", "Playback State: $playbackState")
                        }
                    })
                    playWhenReady = true
                prepare()
            }
        }

        AndroidView(
            factory = { ctx ->
                val view = LayoutInflater.from(ctx).inflate(R.layout.custom_player_view_texture, null, false)
                val playerView = view.findViewById<PlayerView>(R.id.player_view_texture)
                playerView.player = exoPlayer
                playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                view
            },
            modifier = Modifier.fillMaxSize()
        )

        DisposableEffect(exoPlayer) {
            onDispose {
                exoPlayer.release()
            }
        }

        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                Log.d("PlayerEvent", "URL: $streamUrl, Estado Player: $playbackState")
            }

            override fun onPlayerError(error: PlaybackException) {
                Toast.makeText(
                    context,
                    "Error al reproducir el stream: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("PlayerEvent", "URL: $streamUrl, Error Player: ${error.message}", error)
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                Log.d("PlayerEvent", "URL: $streamUrl, Video Size Changed: ${videoSize.width}x${videoSize.height}")
            }

            override fun onSurfaceSizeChanged(width: Int, height: Int) {
                Log.d("PlayerEvent", "URL: $streamUrl, Surface Size Changed: ${width}x${height}")
            }

            override fun onRenderedFirstFrame() {
                Log.d("PlayerEvent", "URL: $streamUrl, Rendered First Frame!")
            }
        })

    } else {
        // TODO: Usar ExoPlayer para HTTP Live Streaming (HLS) u otros formatos
    }
}