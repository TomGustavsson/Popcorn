package com.tomg.popcorn

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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