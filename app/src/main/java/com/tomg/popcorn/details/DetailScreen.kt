package com.tomg.popcorn.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.tomg.popcorn.FadingBottomBox
import com.tomg.popcorn.LoadImageWithUrl
import com.tomg.popcorn.R
import com.tomg.popcorn.explore.ExploreRow
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

@ExperimentalCoilApi
@Composable
fun DetailScreen(viewModel: DetailViewModel){

  val movie = viewModel.movieDetail.value
  val similarMovies = viewModel.similarMovies.value
  val favourites = viewModel.favourites().subscribeAsState(initial = emptyList()).value
  val scrollState = rememberScrollState()

  Column(modifier = Modifier
    .fillMaxSize()
    .background(Colors.popBlack)
    .verticalScroll(scrollState)) {

    Box(modifier = Modifier.fillMaxWidth()){
      LoadImageWithUrl(
        modifier = Modifier
          .width(LocalConfiguration.current.screenWidthDp.dp)
          .height(220.dp),
        url = movie?.backdrop ?: "",
        saved = favourites.map { it.id }.contains(movie?.id?.toInt())){
        movie?.let { movie ->
          if(it){
            viewModel.saveMovie(movie)
          } else {
            viewModel.deleteFavourite(movie)
          }
        }
      }
      FadingBottomBox(Modifier.align(Alignment.BottomCenter))
    }

    Text(text = movie?.title ?: "",
      fontFamily = Fonts.popFont,
      fontWeight = FontWeight.W400,
      fontSize = 22.sp,
      color = Colors.popWhite,
      modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )

    Text(text = "Drama, Fantasy, Sci-fi",
      fontFamily = Fonts.popFont,
      fontWeight = FontWeight.W300,
      fontSize = 12.sp,
      color = Colors.popGreySpecialDark,
      modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
    )

    Text(text = movie?.overview ?: "",
      fontFamily = Fonts.popFont,
      fontWeight = FontWeight.W300,
      fontSize = 16.sp,
      color = Colors.popWhite,
      modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )

    Row(
      Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)) {
      InformationBox(Modifier.weight(1f), "IMDb RATING", movie?.rating ?: "0.0", "/10")
      InformationBox(Modifier.weight(1f), "YOUR RATING", "Rate", "")
      InformationBox(Modifier.weight(1f), "POPULARITY", movie?.votes ?: "0", "")
    }

    ExploreRow(
      list = similarMovies,
      favourites = favourites,
      header = "Similar movies",
      onSave = {
        if(it.first){
          viewModel.saveMovie(it.second)
        } else {
          viewModel.deleteFavourite(it.second)
        }
      },
      onClick = {
        //TODO:
      }
    )

  }
}

@Composable
fun InformationBox(modifier: Modifier = Modifier, title: String, content: String, secondContent: String){
  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(6.dp)) {
    Text(text = title,
      fontFamily = Fonts.popFont,
      fontWeight = FontWeight.W400,
      fontSize = 14.sp,
      color = Colors.popGreySpecialDark)

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
      Icon(
        painter = painterResource(id = R.drawable.ic_star),
        contentDescription = "star",
        modifier = Modifier
          .size(24.dp)
          .padding(end = 2.dp),
        tint = Colors.popRed)

      Text(
        text = content,
        fontFamily = Fonts.popFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        color = Colors.popWhite)

      if(secondContent.isNotEmpty()){
        Text(text = secondContent,
          modifier = Modifier.padding(start = 2.dp ,end = 4.dp),
          fontFamily = Fonts.popFont,
          fontWeight = FontWeight.W500,
          fontSize = 16.sp,
          color = Colors.popGreySpecialDark)
      }
    }
  }
}