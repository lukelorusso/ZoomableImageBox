package com.lukelorusso.zoomableimagebox.ui.view

data class GestureData(
    val angle: Float,
    val zoom: Float,
    val offsetX: Float,
    val offsetY: Float
) {
    val isGestureDetected: Boolean
        get() = this != new()

    companion object {
        const val INITIAL_ANGLE = 0f
        const val INITIAL_ZOOM = 1f
        const val INITIAL_OFFSET = 0f

        fun new(): GestureData =
            GestureData(
                angle = INITIAL_ANGLE,
                zoom = INITIAL_ZOOM,
                offsetX = INITIAL_OFFSET,
                offsetY = INITIAL_OFFSET
            )

        fun GestureData.update(
            angle: Float? = null,
            zoom: Float? = null,
            offsetX: Float? = null,
            offsetY: Float? = null
        ): GestureData =
            GestureData(
                angle = angle ?: this.angle,
                zoom = zoom ?: this.zoom,
                offsetX = offsetX ?: this.offsetX,
                offsetY = offsetY ?: this.offsetY
            )
    }
}
