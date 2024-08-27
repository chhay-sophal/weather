package com.example.weather

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.data.models.SavedLocation
import com.example.weather.ui.components.PermissionRationaleDialog
import com.example.weather.ui.components.RationaleState
import com.example.weather.ui.screens.MainScreen
import com.example.weather.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

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

    // Keeps track of the rationale dialog state, needed when the user requires further rationale
    var rationaleState by remember {
        mutableStateOf<RationaleState?>(null)
    }

    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val permissionGranted by rememberUpdatedState(locationPermissionState.status.isGranted)

    rationaleState?.run { PermissionRationaleDialog(rationaleState = this) }

    // Fetch location when permissions are granted
    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            try {
                val locationResult = fusedLocationClient.lastLocation
                val loc = locationResult.await()
                loc?.let {
                    weatherViewModel.updateLocation(it)
                    Log.d("MainScreen", "Location - Latitude: ${it.latitude}, Longitude: ${it.longitude}")
                    val index = weatherViewModel.saveLocation(SavedLocation(it.latitude, it.longitude))
                }
            } catch (e: SecurityException) {
                Log.e("MainScreen", "Location permission not granted", e)
            }
        } else {
            if (locationPermissionState.status.shouldShowRationale) {
                rationaleState = RationaleState(
                    "Request approximate location access",
                    "In order to use this feature please grant access by accepting " + "the location permission dialog." + "\n\nWould you like to continue?",
                ) { proceed ->
                    if (proceed) {
                        locationPermissionState.launchPermissionRequest()
                    }
                    rationaleState = null
                }
            } else {
                locationPermissionState.launchPermissionRequest()
            }
        }
    }

    // Set up the NavHost with routes
    NavHost(navController = navController, startDestination = "home") {
        // Define the "home" route
        composable("home") {
            MainScreen(navController, weatherViewModel, locationPermissionState, indexToShow = 0)
        }
        composable("home/{index}") { backStackEntry ->
            val indexToShow = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            MainScreen(navController, weatherViewModel, locationPermissionState, indexToShow)
        }
    }
}
