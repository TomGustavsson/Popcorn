package com.tomg.popcorn.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import com.tomg.popcorn.R
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

@Preview
@Composable
fun SnackBarPreview(){
  CustomSnackBar(text = "No internet connection")
}


/** To use snackbarHost pass the scaffolds snackBarHostState into this composable **/
@Composable
fun CustomSnackBarHost(state: SnackbarHostState, content: @Composable () -> Unit) {
  SnackbarHost(state) {
    content()
  }
}

@Composable
fun CustomSnackBar(text: String) {
  Snackbar(
    modifier = Modifier.fillMaxWidth().padding(6.dp),
    backgroundColor = Colors.popRed
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically) {
      Icon(
        painter = painterResource(id = R.drawable.ic_warning),
        contentDescription = "textIcon",
        tint = Colors.popWhite
      )
      Text(
        text = text,
        fontFamily = Fonts.popFont,
        fontWeight = FontWeight.W200,
        fontSize = 16.sp,
        color = Colors.popWhite,
        modifier = Modifier.padding(top = 2.dp, end = 16.dp, start = 8.dp),
        maxLines = 2,
        textAlign = TextAlign.Center
      )
    }
  }
}