package com.example.weather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weather.data.models.SavedLocation
import com.example.weather.ui.components.LocationWeatherPage
import com.example.weather.viewmodel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController = rememberNavController(),
    weatherViewModel: WeatherViewModel = viewModel(),
    lat: String,
    lon: String
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val forecast by weatherViewModel.locationForecast.observeAsState()

    weatherViewModel.fetchForecastByLocation("$lat, $lon")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF050D22),
        contentColor = Color(0xFFE5E5E5),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF050D22),
                    titleContentColor = Color(0xFFE5E5E5),
                    actionIconContentColor = Color(0xFFE5E5E5),
                    navigationIconContentColor = Color(0xFFE5E5E5)
                ),
                title = {},
                navigationIcon = {
                    Text(
                        text = "Cancel",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable{
                                navController.popBackStack()
                            }
                            .padding(10.dp)
                    )
                },
                actions = {
                    Text(
                        text = "Add",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable{
                                val index = weatherViewModel.saveLocation(SavedLocation(lat.toDouble(), lon.toDouble()))
                                navController.navigate("home/$index")
                            }
                            .padding(10.dp)
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            if (forecast != null) {
                LocationWeatherPage(forecast!!)
            } else {
                CircularProgressIndicator()
            }
        }
    }
}