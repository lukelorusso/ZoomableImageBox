package com.lukelorusso.zoomableimagebox.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.lukelorusso.zoomableimagebox.ui.view.ZoomableImageBox


@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun MainScreen() {
    val painter = rememberImagePainter("https://picsum.photos/768/1024") // your precious painter!

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
