package com.example.weather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather.data.models.SavedLocation
import com.example.weather.ui.components.WeatherItem
import com.example.weather.viewmodel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val weatherByLocation by weatherViewModel.todayForecast.observeAsState(emptyList())

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                title = { Text(text = "Weather", fontSize = 30.sp, fontWeight = FontWeight.Bold)},
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add location"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                itemsIndexed(
                    items = weatherByLocation,
                    key = { _, weatherInfo -> "${weatherInfo.location.lat},${weatherInfo.location.lon}" }
                ) {index, weatherInfo ->
                    WeatherItem(
                        weatherInfo = weatherInfo,
                        index = index,
                        onRemove = {weather ->
                            weatherViewModel.removeLocation(SavedLocation(weather.location.lat, weather.location.lon))
                        },
                        onClick = {
                            navController.navigate("home/$index")
                        }
                    )
                }
            }
        }
    }
}
