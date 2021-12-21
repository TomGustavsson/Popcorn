package com.tomg.popcorn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.tomg.popcorn.details.DetailScreen
import com.tomg.popcorn.details.DetailViewModel
import com.tomg.popcorn.explore.ExploreScreen
import com.tomg.popcorn.explore.ExploreViewModel
import com.tomg.popcorn.favourites.FavouriteScreen
import com.tomg.popcorn.favourites.FavouriteViewModel
import com.tomg.popcorn.ui.theme.Colors
import com.tomg.popcorn.ui.theme.PopcornTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val exploreViewModel: ExploreViewModel by viewModels()
  private val favouriteViewModel: FavouriteViewModel by viewModels()
  private val detailViewModel: DetailViewModel by viewModels()

  @ExperimentalCoilApi
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
                  icon = {  Icon(painter = painterResource(screen.drawableRes), contentDescription = "Info") },
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
        ) { innerPadding ->

          NavHost(
            navController = navController,
            startDestination = "explore"
          ) {

            composable(Screen.Explore.route) {
              Box(modifier = Modifier.padding(innerPadding)) {
                ExploreScreen(exploreViewModel, navController = navController)
              }
            }

            composable(Screen.Favourites.route) {
              Box(modifier = Modifier.padding(innerPadding)) {
                FavouriteScreen(favouriteViewModel)
              }
            }

            composable(
              Screen.Detail.route,
              arguments = listOf(navArgument("movieId") { type = NavType.StringType })) {
              it.arguments?.getString("movieId")?.let {
                detailViewModel.loadMovie(it)
              }
                Box(modifier = Modifier.padding(innerPadding)) {
                  DetailScreen(detailViewModel)
                }
            }
          }
        }
      }
    }
  }

  sealed class Screen(val route: String, @StringRes val resourceId: Int, val drawableRes: Int) {
    object Explore : Screen("explore", R.string.explore, R.drawable.ic_star)
    object Favourites : Screen("favourites", R.string.favourites, R.drawable.ic_saved)
    object Detail : Screen("detail/{movieId}", R.string.detail, R.drawable.ic_check)
  }
}
