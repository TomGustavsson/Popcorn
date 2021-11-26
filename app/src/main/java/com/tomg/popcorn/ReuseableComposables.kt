package com.tomg.popcorn

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

@Composable
fun PopcornToolbar(title: String){
  TopAppBar(
    title = {
      Text(
        title,
        fontFamily = Fonts.popFont,
        fontWeight = FontWeight.W100,
        fontSize = 18.sp,
        color = Colors.popRed,
        modifier = Modifier.padding(10.dp)) },
    backgroundColor = Colors.popWhite)
}

@Composable
fun DrawableInText(
  modifier: Modifier = Modifier,
  end: Boolean,
  drawableRes: Int,
  drawableColor: Color = Colors.popWhite,
  text: String,
  textColor: Color = Colors.popWhite,
  fontFamily: FontFamily = Fonts.popFont,
  fontWeight: FontWeight = FontWeight.W300,
  fontSize: TextUnit = 10.sp,
  iconSize: TextUnit = 16.sp
) {
  val myId = "inlineContent"
  val textWithIcon = buildAnnotatedString {
    if (end) {
      append(text)
      appendInlineContent(myId, "[icon]")
    } else {
      /** icon at start **/
      appendInlineContent(myId, "[icon]")
      append(text)
    }
  }

  val inlineContent = mapOf(
    Pair(myId, InlineTextContent(
      Placeholder(
        width = iconSize,
        height = iconSize,
        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
      )
    ) {
      Icon(
        painter = painterResource(drawableRes),
        contentDescription = "textIcon",
        tint = drawableColor,
        modifier = if (end) Modifier.padding(start = 4.dp, top = 2.dp) else Modifier.padding(end = 4.dp, top = 2.dp)
      )
    })
  )
  Text(
    text = textWithIcon,
    inlineContent = inlineContent,
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    color = textColor,
    modifier = modifier
  )
}

@ExperimentalCoilApi
@Composable
fun LoadImageWithUrl(width: Int, height: Int, url: String){
  Box {
    CircularProgressIndicator(color = Colors.popRed, modifier = Modifier.align(Alignment.Center))
    Image(
      painter = rememberImagePainter(
        data = url,
        builder = {
          error(R.drawable.ic_placeholder_image)
        }),
      contentScale = ContentScale.Crop,
      contentDescription = null,
      modifier = Modifier
        .height(height.dp)
        .width(width.dp)
        .align(Alignment.CenterStart)
    )
  }
}

class MyFirstShape : Shape {
  override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
    val trianglePath = Path().apply {
      // Moves to top center position
      moveTo(size.width / 2f, 0f)
      // Add line to right corner above circle
      lineTo(x = size.width, y = size.height)
      //Add line to left corner above circle
      lineTo(x = 0f, y = size.height)
    }
    return Outline.Generic(path = trianglePath)
  }
}