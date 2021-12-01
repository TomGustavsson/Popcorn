package com.tomg.popcorn

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import com.tomg.popcorn.api.Api
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.FlagShape
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
fun LoadImageWithUrl(width: Int, height: Int, url: String, saved: Boolean, onClick: (Boolean) -> Unit){
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
    FlagShapedBox(
      modifier = Modifier
        .align(Alignment.TopEnd)
        .alpha(0.8f)
        .clickAbleModifier(true){
          onClick.invoke(!saved)
        }, saved)
  }
}

@Composable
fun FlagShapedBox(modifier: Modifier = Modifier, saved: Boolean){
  Box(modifier = modifier
    .height(50.dp)
    .width(35.dp)
    .clip(FlagShape())
    .background(if(saved)Colors.popRed else Colors.popLighDark)
  ){
    Icon(
      painter = painterResource(id = if(saved) R.drawable.ic_check else R.drawable.ic_plus),
      contentDescription = "save",
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(6.dp)
      ,
      tint = Colors.popWhite
    )
  }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver", "UnnecessaryComposedModifier")
fun Modifier.clickAbleModifier(bounded: Boolean, onClick: () -> Unit): Modifier = composed {
  Modifier.clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = rememberRipple(bounded = bounded),
    onClick = { onClick.invoke() }
  )
}