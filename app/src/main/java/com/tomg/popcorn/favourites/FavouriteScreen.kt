package com.tomg.popcorn.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tomg.popcorn.composables.DrawableInText
import com.tomg.popcorn.composables.FadingBottomBox
import com.tomg.popcorn.composables.LoadImageWithUrl
import com.tomg.popcorn.R
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

/** Previews **/

@ExperimentalCoilApi
@Preview
@Composable
fun FavouriteRowPreview(){
  FavouriteRow(favourite = Favourite(1234, "Shawshank redemption", "", emptyList(), "", "9.4", "200.001", "1993-07-24", ""), true){
  }
}
/************************************/

@ExperimentalCoilApi
@Composable
fun FavouriteScreen(viewModel: FavouriteViewModel){
  val favourites = viewModel.favourites().subscribeAsState(initial = emptyList())
  LazyColumn(modifier = Modifier
    .fillMaxSize()
    .background(Colors.popBlack), contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)){
    items(favourites.value.size){ index ->
      FavouriteRow(favourites.value[index], true){
        viewModel.delete(it)
      }
    }
  }
}

@ExperimentalCoilApi
@Composable
fun FavouriteRow(favourite: Favourite, isSaved: Boolean, callback: (Favourite) -> Unit){
  StandardCard {
    Box {
      Row {
        LoadImageWithUrl(modifier = Modifier
          .width(100.dp)
          .height(150.dp), url = favourite.poster, saved = isSaved){
          callback.invoke(favourite)
        }
        Column {
          Text(
            text = favourite.title,
            fontFamily = Fonts.popFont,
            fontWeight = FontWeight.W300,
            fontSize = 18.sp,
            color = Colors.popWhite,
            modifier = Modifier.padding(end = 16.dp, start = 16.dp, top = 6.dp)
          )
          DrawableInText(
            end = false,
            drawableRes = R.drawable.ic_star,
            drawableColor = Colors.popRed,
            textColor = Colors.popGreySpecialDark,
            text = favourite.rating,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            fontSize = 18.sp,
            iconSize = 24.sp
          )
        }
      }
      FadingBottomBox(Modifier.align(Alignment.BottomCenter))
    }
    }
}

@Composable
fun StandardCard(content: @Composable () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 6.dp, start = 4.dp, end = 4.dp),
    shape = RoundedCornerShape(2.dp),
    backgroundColor = Colors.popLighDark,
    elevation = 2.dp
  ) {
    content()
  }
}

@Preview
@Composable
fun EmptyFavouriteScreen(){
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {

    Text(
      text = "You have no favourite movies saved. \n Go to explore screen to and save a couple",
      textAlign = TextAlign.Center,
      color = Colors.popWhite,
      lineHeight = 24.sp)
    Button(
      onClick = {  },
      modifier = Modifier.padding(8.dp),
      shape = RoundedCornerShape(18.dp),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = Colors.popRed,
        contentColor = MaterialTheme.colors.surface
      )
    ) {
      Text(text = "Explore view")
    }
  }
}