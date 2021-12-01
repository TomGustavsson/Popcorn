package com.tomg.popcorn.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.tomg.popcorn.DrawableInText
import com.tomg.popcorn.LoadImageWithUrl
import com.tomg.popcorn.R
import com.tomg.popcorn.api.Api
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.explore.ExploreViewModel
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

/** Preview purpose **/

@ExperimentalCoilApi
@Preview
@Composable
fun ExploreRowPreview() {
  ExploreRow(list = ExploreViewModel.getMockData(), emptyList(),"Popular movies"){
  }
}

/********************************************************/

@ExperimentalCoilApi
@Composable
fun ExploreScreen(viewModel: ExploreViewModel){
  val popular = viewModel.popularMovies.value
  val favourites = viewModel.favourites().subscribeAsState(initial = emptyList()).value
  LazyColumn(modifier = Modifier
    .fillMaxSize()
    .background(Colors.popBlack)){
    item {
      ExploreRow(list = popular, favourites = favourites,"Popular movies"){
        if(it.first){
          viewModel.saveMovie(it.second)
        } else {
          viewModel.deleteFavourite(it.second)
        }
      }
    }
    item {
      ExploreRow(list = popular, favourites = favourites,"Drama"){
        if(it.first){
          viewModel.saveMovie(it.second)
        } else {
          viewModel.deleteFavourite(it.second)
        }
      }
    }
    item {
      ExploreRow(list = popular, favourites = favourites,"Drama"){
        if(it.first){
          viewModel.saveMovie(it.second)
        } else {
          viewModel.deleteFavourite(it.second)
        }
      }
    }
  }
}

@ExperimentalCoilApi
@Composable
fun ExploreRow(list: List<Api.Movie>, favourites: List<Favourite>,header: String, onClick: (Pair<Boolean,Api.Movie>) -> Unit){
  Column(Modifier.background(Colors.popBlack)){
    Text(text = header,
      fontFamily = Fonts.popFont,
      fontWeight = FontWeight.W400,
      fontSize = 16.sp,
      color = Colors.popWhite,
      modifier = Modifier.padding(12.dp)
    )
    LazyRow(modifier = Modifier
      .wrapContentHeight()){
      items(list){ movie ->
        ExploreItem(movie, favourites){
          onClick.invoke(it)
        }
      }
    }
  }
}

@ExperimentalCoilApi
@Composable
fun ExploreItem(movie: Api.Movie, favourites: List<Favourite>, onClick: (Pair<Boolean,Api.Movie>) -> Unit){
  Card(
    modifier = Modifier
      .width(150.dp)
      .height(300.dp)
      .padding(4.dp),
    backgroundColor = Colors.popLighDark,
    elevation = 2.dp,
    shape = RoundedCornerShape(2.dp)
  ) {
    Column {
      LoadImageWithUrl(width = 150, height = 220, url = movie.poster, saved = favourites.map { it.id }.contains(movie.id.toInt())){
        onClick.invoke(Pair(it,movie))
      }
      DrawableInText(
        end = false,
        drawableRes = R.drawable.ic_star,
        drawableColor = Colors.popRed,
        textColor = Colors.popGreySpecialDark,
        text = movie.rating,
        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
      )
      Text(
        text = movie.title,
        fontFamily = Fonts.popFont,
        fontWeight = FontWeight.W200,
        fontSize = 12.sp,
        color = Colors.popWhite,
        modifier = Modifier.padding(end = 4.dp, start = 4.dp),
        maxLines = 2
      )
    }
  }
}