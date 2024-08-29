package com.example.weather

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.ui.screens.AboutUsScreen
import com.example.weather.ui.screens.ChooseLocationScreen
import com.example.weather.ui.screens.DetailScreen
import com.example.weather.ui.screens.FirstScreen
import com.example.weather.ui.screens.ListScreen
import com.example.weather.ui.screens.MainScreen
import com.example.weather.ui.screens.NextSevenDayScreen
import com.example.weather.ui.screens.SearchScreen
import com.example.weather.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    val weatherViewModel: WeatherViewModel = viewModel()

    // Approximate location access is sufficient for most of use cases
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    NavHost(navController = navController, startDestination = "first-screen") {
        composable("first-screen") {
            FirstScreen(navController)
        }
        composable("choose-location") {
            ChooseLocationScreen(navController, weatherViewModel, locationPermissionState)
        }
        composable("home") {
            MainScreen(navController, weatherViewModel, locationPermissionState, indexToShow = 0)
        }
        composable("home/{index}") { backStackEntry ->
            val indexToShow = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            MainScreen(navController, weatherViewModel, locationPermissionState, indexToShow)
        }
        composable("list-view") {
            ListScreen(navController, weatherViewModel)
        }
        composable("search") {
            SearchScreen(navController, weatherViewModel)
        }
        composable("next-seven-day/{lat}/{lon}"){backStakeEntry ->
            val lat = backStakeEntry.arguments?.getString("lat")
            val lon = backStakeEntry.arguments?.getString("lon")
            if (lat != null && lon != null) {
                NextSevenDayScreen(navController, weatherViewModel, lat, lon)
            }

        }
        composable("detail/{lat}/{lon}") { backStakeEntry ->
            val lat = backStakeEntry.arguments?.getString("lat")
            val lon = backStakeEntry.arguments?.getString("lon")
            if (lat != null && lon != null) {
                DetailScreen(navController, weatherViewModel, lat, lon)
            }
        }
        composable("about-us") {
            AboutUsScreen(navController)
        }
    }
}
