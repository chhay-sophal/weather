package com.example.weather.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather.R
import com.example.weather.data.models.SavedLocation
import com.example.weather.ui.components.PermissionRationaleDialog
import com.example.weather.ui.components.RationaleState
import com.example.weather.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChooseLocationScreen(navController: NavController, weatherViewModel: WeatherViewModel, locationPermissionState: PermissionState) {
    // Keeps track of the rationale dialog state, needed when the user requires further rationale
    val rationaleState by remember {
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
                    navController.navigate("home")
                }
            } catch (e: SecurityException) {
                Log.e("MainScreen", "Location permission not granted", e)
            }
        }

//        For RationalState
//        else {
//            if (locationPermissionState.status.shouldShowRationale) {
//                rationaleState = RationaleState(
//                    "Request approximate location access",
//                    "In order to use this feature please grant access by accepting " + "the location permission dialog." + "\n\nWould you like to continue?",
//                ) { proceed ->
//                    if (proceed) {
//                        locationPermissionState.launchPermissionRequest()
//                    }
//                    rationaleState = null
//                }
//            } else {
//                locationPermissionState.launchPermissionRequest()
//            }
//        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF050D22),
        contentColor = Color(0xFFE5E5E5)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // World Icon
                Image(
                    painter = painterResource(id = R.drawable.pin),
                    contentDescription = "World Icon",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Add some text above the "Get Device Location" button
                Text(
                    text = "Find your current location or select one manually",
                    color = Color(0xFFE5E5E5),
                    modifier = Modifier.padding(horizontal = 30.dp),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Get Device Location Button
                Button(
                    onClick = {
                        locationPermissionState.launchPermissionRequest()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(text = "Get Device Location")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // "Or" line between buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = Color(0xFFE5E5E5)
                    )

                    Text(
                        text = "OR",
                        fontSize = 12.sp,
                        color = Color(0xFFE5E5E5),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = Color(0xFFE5E5E5)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Choose Manually Button
                Button(
                    onClick = {
                        navController.navigate("search")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(text = "Choose Manually")
                }
            }
        }
    }
}
