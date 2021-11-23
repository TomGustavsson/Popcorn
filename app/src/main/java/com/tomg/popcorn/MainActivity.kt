package com.tomg.popcorn

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tomg.popcorn.models.Favourite
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.PopcornTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.exp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val exploreViewModel: ExploreViewModel by viewModels()

  @SuppressLint("RememberReturnType")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val items = listOf(
      Screen.Explore,
      Screen.Favourites,
    )

    setContent {
      PopcornTheme {
        val navController = rememberNavController()
        Scaffold(
          backgroundColor = Colors.popWhite,
          bottomBar = {
            BottomNavigation {
              val navBackStackEntry by navController.currentBackStackEntryAsState()
              val currentDestination = navBackStackEntry?.destination
              items.forEach { screen ->
                BottomNavigationItem(
                  icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                  label = { Text(stringResource(screen.resourceId)) },
                  selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                  onClick = {
                    navController.navigate(screen.route) {
                      popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                      }
                      launchSingleTop = true
                      restoreState = true
                    }
                  }
                )
              }
            }
          }
        ) {

          NavHost(
            navController = navController,
            startDestination = "explore"
          ) {

            composable(Screen.Explore.route) {
              ExploreScreen(exploreViewModel)
            }

            composable(Screen.Favourites.route) {
              FavouriteScreen(favourites = mockData())
            }
          }
        }
      }
    }
  }

  private fun mockData(): List<Favourite>{
    return listOf(
      Favourite(
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
      ), Favourite(
        title = "Goodfellas",
        year = "1998",
        released = "24 June 1998",
        genre = "Action",
        poster = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
        imdbRating = "9,3",
        imdbVotes = "2,485,751",
        usersRating = 0
      )
    )
  }
  sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Explore : Screen("explore", R.string.explore)
    object Favourites : Screen("favourites", R.string.favourites)
  }
}
