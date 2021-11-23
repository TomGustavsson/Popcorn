package com.tomg.popcorn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomg.popcorn.api.Api
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

@Composable
fun ExploreScreen(viewModel: ExploreViewModel){
  viewModel.loadPopularMovies()
}

@Preview
@Composable
fun ExploreRowPreview() {
  ExploreRow(list = ExploreViewModel.getMockData())
}
@Composable
fun ExploreRow(list: List<Api.Movie>){
  LazyRow(modifier = Modifier.wrapContentHeight()){
    items(list){ movie ->
      ExploreItem(movie)
    }
  }
}

@Composable
fun ExploreItem(movie: Api.Movie){
  Card(
    modifier = Modifier
      .width(100.dp)
      .height(200.dp)
      .padding(start = 4.dp, end = 4.dp),
    backgroundColor = Colors.popBlack,
    elevation = 2.dp,
    shape = RoundedCornerShape(2.dp)
  ) {
    Column() {
      Box(modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)){
        Image(
          painter = painterResource(id = R.drawable.test_poster),
          contentDescription = "test",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
      }
      Text(
        text = movie.title,
        fontFamily = Fonts.popFont,
        fontWeight = FontWeight.W100,
        fontSize = 8.sp,
        color = Colors.popWhite,
        modifier = Modifier.padding(top = 2.dp, end = 6.dp, start = 6.dp),
        maxLines = 2
      )
    }
  }
}