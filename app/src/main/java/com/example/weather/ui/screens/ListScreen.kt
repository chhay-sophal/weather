package com.example.weather.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather.data.models.SavedLocation
import com.example.weather.data.models.WeatherRoot
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
        SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744)
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
//        Icon(
//            Icons.Default.Archive,
//            contentDescription = "Archive"
//        )
        Spacer(modifier = Modifier)
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherItem(
    weatherInfo: WeatherRoot,
    index: Int,
    onRemove: (WeatherRoot) -> Unit,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val currentItem by rememberUpdatedState(weatherInfo)
    if (index == 0) {
        // Disable swipe-to-dismiss for the first item by directly showing WeatherCard
        WeatherCard(
            weatherInfo = weatherInfo,
            onClick = onClick,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)) // Rounded corners
                .background(Color(0x1AFFFFFF))
                .clickable(onClick = onClick)
        )
    } else {
        val dismissState = rememberSwipeToDismissBoxState(
            confirmValueChange = {value ->
                when(value) {
//                    SwipeToDismissBoxValue.StartToEnd -> {
//                        return@rememberSwipeToDismissBoxState false
//                    }
                    SwipeToDismissBoxValue.EndToStart -> {
                        onRemove(currentItem)
                        Toast.makeText(context, "Location removed", Toast.LENGTH_SHORT).show()
                        return@rememberSwipeToDismissBoxState true
                    }
//                    SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                    else -> false
                }
//                return@rememberSwipeToDismissBoxState true
            },
            // positional threshold of 25%
            positionalThreshold = { it * .25f }
        )

        // Reset dismiss state when the list size changes
        LaunchedEffect(weatherInfo) {
            dismissState.reset() // This will ensure that the state is fresh for each new item
        }

        SwipeToDismissBox(
            state = dismissState,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)) // Rounded corners
                .background(Color(0x1AFFFFFF))
                .clickable(onClick = onClick),
            enableDismissFromStartToEnd = false,  // Disable swipe from start to end
            enableDismissFromEndToStart = true,   // Enable swipe from end to start
            backgroundContent = { DismissBackground(dismissState)},
            content = {
                WeatherCard(weatherInfo, onClick)
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherCard(
    weatherInfo: WeatherRoot,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)) // Rounded corners
            .background(Color(0xFF2D3646))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(15.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = weatherInfo.location.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = weatherInfo.current.condition.text)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${weatherInfo.current.tempC.toInt()}°", fontSize = 45.sp, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "H: ${weatherInfo.forecast.forecastday[0].day.maxtempC.toInt()}°", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "L: ${weatherInfo.forecast.forecastday[0].day.mintempC.toInt()}°", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
