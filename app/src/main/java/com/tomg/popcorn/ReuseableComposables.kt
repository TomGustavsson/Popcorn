package com.tomg.popcorn

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
  fontSize: TextUnit = 8.sp
) {
  val myId = "inlineContent"
  val textWithIcon = buildAnnotatedString {
    if (end) {
      append(text)
      appendInlineContent(myId, "[icon]")
    } else {
      /** icon at start **/
      /** icon at start **/
      appendInlineContent(myId, "[icon]")
      append(text)
    }
  }

  val inlineContent = mapOf(
    Pair(myId, InlineTextContent(
      Placeholder(
        width = 16.sp,
        height = 16.sp,
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