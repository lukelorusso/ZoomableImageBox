package com.lukelorusso.zoomableimagebox.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.lukelorusso.zoomableimagebox.ui.theme.AppTheme
import com.lukelorusso.zoomableimagebox.ui.view.ZoomableImageBox


@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun MainScreen() {
    val painter = rememberImagePainter(
        "https://lukelorusso.com/wp-content/uploads/2022/03/youtube_wall.png"
    ) // your precious painter!

    Surface(
        content = {
            ZoomableImageBox(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                painter = painter,
                shouldRotate = true
            )
        }
    )
}

@Preview
@Composable
private fun MainPreview() {
    AppTheme {
        MainScreen()
    }
}
