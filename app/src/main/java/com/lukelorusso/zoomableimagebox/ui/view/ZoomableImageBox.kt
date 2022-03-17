package com.lukelorusso.zoomableimagebox.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private const val INITIAL_ANGLE = 0f
private const val INITIAL_ZOOM = 1f
private const val INITIAL_OFFSET = 0f
private const val ICON_PADDING = 8

/**
 * @author Copyright (C) 2022 Luke Lorusso - https://lukelorusso.com
 * Licensed under the Apache License Version 2.0
 *
 * This [Composable] [Box] allows you to render an [Image] which can be zoomed in/out and rotated.
 * @param modifier applied to the Box
 * @param contentAlignment determines the ResetButton position
 * @param contentDescription of your image
 * @param painter resource of your image
 * @param imageContentScale can be changed
 * @param shouldRotate can be toggled
 * @param resetIconContent can be customized
 */
@Composable
fun ZoomableImageBox(
    modifier: Modifier,
    contentAlignment: Alignment = Alignment.BottomEnd,
    contentDescription: String? = null,
    painter: Painter,
    imageContentScale: ContentScale = ContentScale.Inside,
    shouldRotate: Boolean = false,
    resetIconContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = stringResource(id = android.R.string.cancel),
            tint = MaterialTheme.colors.primary
        )
    }
) {
    val angle = remember { mutableStateOf(INITIAL_ANGLE) }
    val zoom = remember { mutableStateOf(INITIAL_ZOOM) }
    val offsetX = remember { mutableStateOf(INITIAL_OFFSET) }
    val offsetY = remember { mutableStateOf(INITIAL_OFFSET) }
    val showResetButton = remember { mutableStateOf(false) }

    return Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                .graphicsLayer(
                    scaleX = zoom.value,
                    scaleY = zoom.value,
                    rotationZ = angle.value
                )
                .pointerInput(Unit) {
                    detectTransformGestures(
                        onGesture = { _, pan, gestureZoom, gestureRotate ->
                            if (shouldRotate) angle.value += gestureRotate
                            zoom.value *= gestureZoom
                            val x = pan.x * zoom.value
                            val y = pan.y * zoom.value
                            val angleRad = angle.value * PI / 180.0
                            offsetX.value += (x * cos(angleRad) - y * sin(angleRad)).toFloat()
                            offsetY.value += (x * sin(angleRad) + y * cos(angleRad)).toFloat()
                            showResetButton.value = true
                        }
                    )
                },
            contentDescription = contentDescription,
            painter = painter,
            contentScale = imageContentScale
        )

        if (showResetButton.value) {
            IconButton(
                modifier = Modifier
                    .padding(ICON_PADDING.dp)
                    .background(MaterialTheme.colors.surface, shape = CircleShape),
                onClick = {
                    angle.value = INITIAL_ANGLE
                    zoom.value = INITIAL_ZOOM
                    offsetX.value = INITIAL_OFFSET
                    offsetY.value = INITIAL_OFFSET
                    showResetButton.value = false
                },
                content = resetIconContent
            )
        }
    }
}
