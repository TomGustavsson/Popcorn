package com.tomg.popcorn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomg.popcorn.models.Favourite
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts

@Preview
@Composable
fun FavouritePreview(){
  val list = listOf(Favourite(
    title = "Shawshank redemption",
    year = "1994",
    released = "14 Oct 1994",
    genre = "Drama",
    poster = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
    imdbRating = "9,3",
    imdbVotes = "2,485,751",
    usersRating = 0),
    Favourite(
    title = "Interstellar",
    year = "2015",
    released = "09 Dec 2015",
    genre = "Sci-fi",
    poster = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
    imdbRating = "8,8",
    imdbVotes = "1,485,751",
    usersRating = 0
  ),Favourite(
    title = "Goodfellas",
    year = "1998",
    released = "24 June 1998",
    genre = "Action",
    poster = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
    imdbRating = "9,3",
    imdbVotes = "2,485,751",
    usersRating = 0
  ))
  FavouriteScreen(favourites = list)
}

@Composable
fun FavouriteScreen(favourites: List<Favourite>){
  LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)){
    items(favourites){ favourite ->
      FavouriteRow(favourite)
    }
  }
}

@Composable
fun FavouriteRow(favourite: Favourite){
  StandardCard {
    Row {
      LoadImageWithUrl(imageSize = 160, url = "")
      Text(
        text = favourite.title,
        fontFamily = Fonts.popFont,
        fontWeight = FontWeight.W100,
        fontSize = 18.sp,
        modifier = Modifier.padding(end = 16.dp, start = 16.dp, top = 6.dp)
      )
    }
  }
}

@Composable
fun LoadImageWithUrl(imageSize: Int, url: String){
  Box(modifier = Modifier
    .wrapContentWidth()
    .height(imageSize.dp)){
    CircularProgressIndicator(
      color = Colors.popRed,
      modifier = Modifier.align(Alignment.Center)
    )
    Image(
      painter = painterResource(id = R.drawable.test_poster),
      contentDescription = "moviePoster",
      modifier = Modifier.align(Alignment.Center)
    )
  }
}

@Composable
fun StandardCard(content: @Composable () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 6.dp, start = 4.dp, end = 4.dp),
    shape = RoundedCornerShape(2.dp),
    backgroundColor = Colors.popWhite,
    elevation = 2.dp
  ) {
    content()
  }
}