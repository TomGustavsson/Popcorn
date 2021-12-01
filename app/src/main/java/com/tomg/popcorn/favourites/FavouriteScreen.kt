package com.tomg.popcorn.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.tomg.popcorn.LoadImageWithUrl
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

/** Previews **/

@ExperimentalCoilApi
@Preview
@Composable
fun FavouriteRowPreview(){
  FavouriteRow(favourite = Favourite(1234, "Shawshank redemption", "", emptyList(), "", "9.4", "200.001", "1993-07-24", "")){
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
      FavouriteRow(favourites.value[index]){
        viewModel.delete(it)
      }
    }
  }
}

@ExperimentalCoilApi
@Composable
fun FavouriteRow(favourite: Favourite, callback: (Favourite) -> Unit){
  StandardCard {
    Row {
      LoadImageWithUrl(width = 100, height = 150, url = favourite.poster, saved = true){
        callback.invoke(favourite)
      }
      Text(
        text = favourite.title,
        fontFamily = Fonts.popFont,
        fontWeight = FontWeight.W300,
        fontSize = 18.sp,
        color = Colors.popWhite,
        modifier = Modifier.padding(end = 16.dp, start = 16.dp, top = 6.dp)
      )
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