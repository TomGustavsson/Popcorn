package com.tomg.popcorn.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.tomg.popcorn.DrawableInText
import com.tomg.popcorn.LoadImageWithUrl
import com.tomg.popcorn.MainActivity
import com.tomg.popcorn.R
import com.tomg.popcorn.api.Api
import com.tomg.popcorn.clickAbleModifier
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.Fonts
import java.net.URLEncoder

/** Preview purpose **/

@ExperimentalCoilApi
@Preview
@Composable
fun ExploreRowPreview() {
  ExploreRow(list = ExploreViewModel.getMockData(), emptyList(),"Popular movies",{},{})
}

/********************************************************/

@ExperimentalCoilApi
@Composable
fun ExploreScreen(viewModel: ExploreViewModel, navController: NavController){
  val movieList = viewModel.movieList.value.toList()
  val favourites = viewModel.favourites().subscribeAsState(initial = emptyList()).value

  val query = viewModel.query.value

  Column(Modifier.background(Colors.popBlack)) {
    TextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 14.dp, bottom = 14.dp, end = 6.dp, start = 6.dp),
      shape = RoundedCornerShape(8.dp),
      trailingIcon = {
        Icon(Icons.Filled.Search, "", tint = Colors.popBlack)
      },
      value = query,
      onValueChange = { newValue ->
      viewModel.changeQuery(newValue)
      },
      placeholder = { Text(text = "Search movies by title..")},
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        backgroundColor = Colors.popWhite,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
      ),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search
      ),
      keyboardActions = KeyboardActions(onSearch = { viewModel.searchMovies() })
    )
      LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(Colors.popBlack)){
        items(movieList.size){ index ->
          ExploreRow(
            list = movieList[index].second,
            favourites = favourites,
            header = movieList[index].first,
            onSave = {
              if(it.first){
                viewModel.saveMovie(it.second)
              } else {
                viewModel.deleteFavourite(it.second)
              }
            },
            onClick = {
              navController.navigate(MainActivity.Screen.Detail.route.replace("{movieId}", it.id))
            })
        }
      }
    }
  }

@ExperimentalCoilApi
@Composable
fun ExploreRow(list: List<Api.Movie>, favourites: List<Favourite>,header: String, onSave: (Pair<Boolean,Api.Movie>) -> Unit, onClick: (Api.Movie) -> Unit){
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
        ExploreItem(movie, favourites, onSave = {
          onSave.invoke(it)
        },
          onClick = {
          onClick.invoke(it)
        }
        )
      }
    }
  }
}

@ExperimentalCoilApi
@Composable
fun ExploreItem(movie: Api.Movie, favourites: List<Favourite>, onSave: (Pair<Boolean,Api.Movie>) -> Unit, onClick: (Api.Movie) -> Unit){
  Card(
    modifier = Modifier
      .clickAbleModifier(true){
        onClick.invoke(movie)
      }
      .width(150.dp)
      .height(300.dp)
      .padding(4.dp),
    backgroundColor = Colors.popLighDark,
    elevation = 2.dp,
    shape = RoundedCornerShape(2.dp)
  ) {
    Column {
      LoadImageWithUrl(
        modifier = Modifier.width(150.dp).height(220.dp),
        url = movie.poster,
        saved = favourites.map { it.id }.contains(movie.id.toInt())){
        onSave.invoke(Pair(it,movie))
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