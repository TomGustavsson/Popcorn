package com.tomg.popcorn.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
  small = RoundedCornerShape(4.dp),
  medium = RoundedCornerShape(4.dp),
  large = RoundedCornerShape(0.dp)
)

class FlagShape : Shape {
  override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
    val flagPath = Path().apply {
      moveTo(size.width, size.height)
      lineTo(size.width / 2, (size.height - size.height / 3))
      lineTo(x = 0f, size.height)
      lineTo(x = 0f, y = 0f)
      lineTo(x = size.width, y = 0f)
    }
    return Outline.Generic(path = flagPath)
  }
}