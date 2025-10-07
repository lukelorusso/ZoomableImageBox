package com.lukelorusso.zoomableimagebox.ui.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.*

private const val INITIAL_ANGLE = 0f
private const val INITIAL_ZOOM = 1f
private const val INITIAL_OFFSET = 0f
private const val ICON_PADDING = 8

/**
 * @author Copyright (C) 2022 Luke Lorusso - https://lukelorusso.com
 * Licensed under the Apache License Version 2.0
 *
 * This [Composable] [Box] allows you to render an [Image] which can be zoomed in/out and rotated.
 * @param modifier applied to the [Box]
 * @param contentAlignment applied to the [Box]: determines the reset [IconButton] position
 * @param contentDescription of your [Image]
 * @param bitmap to draw in your [Image]
 * @param painter drawable resource of your [Image] (can be used instead of bitmap)
 * @param imageContentScale of your [Image]: can be changed
 * @param shouldRotate your [Image]: can be toggled on/off
 * @param showResetIconButton can be toggled on/off
 * @param resetIconButtonModifier is the customizable modifier of the [IconButton], which contains
 *  the resetIconButtonContent (useful if showResetButton is true)
 * @param resetIconButtonContent is the customizable content of the [IconButton] (useful if
 *  showResetButton is true)
 */
@Composable
fun ZoomableImageBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.BottomEnd,
    contentDescription: String? = null,
    bitmap: Bitmap? = null,
    painter: Painter? = null,
    imageContentScale: ContentScale = ContentScale.Inside,
    shouldRotate: Boolean = false,
    showResetIconButton: Boolean = true,
    resetIconButtonModifier: Modifier = Modifier
        .padding(ICON_PADDING.dp)
        .background(MaterialTheme.colors.surface, shape = CircleShape),
    resetIconButtonContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = stringResource(id = android.R.string.cancel),
            tint = MaterialTheme.colors.primary
        )
    }
) {
    //region properties
    var angle by remember { mutableFloatStateOf(INITIAL_ANGLE) }
    var zoom by remember { mutableFloatStateOf(INITIAL_ZOOM) }
    var offsetX by remember { mutableFloatStateOf(INITIAL_OFFSET) }
    var offsetY by remember { mutableFloatStateOf(INITIAL_OFFSET) }
    var isGestureDetected by remember { mutableStateOf(false) }
    //endregion

    return Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        val imageModifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .graphicsLayer(
                scaleX = zoom,
                scaleY = zoom,
                rotationZ = angle
            )
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { _, pan, gestureZoom, gestureRotate ->
                        if (shouldRotate) angle += gestureRotate
                        zoom *= gestureZoom
                        val x = pan.x * zoom
                        val y = pan.y * zoom
                        val angleRad = angle * PI / 180.0
                        offsetX += (x * cos(angleRad) - y * sin(angleRad)).toFloat()
                        offsetY += (x * sin(angleRad) + y * cos(angleRad)).toFloat()
                        isGestureDetected = true
                    }
                )
            }

        //region Image
        when {
            bitmap != null -> Image(
                modifier = imageModifier,
                contentDescription = contentDescription,
                bitmap = bitmap.asImageBitmap(),
                contentScale = imageContentScale
            )

            painter != null -> Image(
                modifier = imageModifier,
                contentDescription = contentDescription,
                painter = painter,
                contentScale = imageContentScale
            )

            else -> {
            }
        }

        //endregion

        //region reset button
        if (showResetIconButton && isGestureDetected) {
            IconButton(
                modifier = resetIconButtonModifier,
                onClick = {
                    angle = INITIAL_ANGLE
                    zoom = INITIAL_ZOOM
                    offsetX = INITIAL_OFFSET
                    offsetY = INITIAL_OFFSET
                    isGestureDetected = false
                },
                content = resetIconButtonContent
            )
        }
        //endregion
    }
}
