package com.example.weather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weather.R
import com.example.weather.data.models.Forecastday
import com.example.weather.data.models.WeatherRoot
import com.example.weather.viewmodel.WeatherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NextSevenDayScreen(
    navController: NavController = rememberNavController(),
    weatherViewModel: WeatherViewModel = viewModel(),
    lat: String,
    lon: String
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val forecast by weatherViewModel.sevenDayForecast.observeAsState()

    weatherViewModel.fetchSevenDayForecast("$lat, $lon")
//    println(forecast)

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
                title = { Text(text = "")},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (forecast != null) {
                NextSevenDayWeatherCard(forecast!!)
            } else {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
fun NextSevenDayWeatherCard(forecast: WeatherRoot) {
    val tomorrow = forecast.forecast.forecastday[1]

    Column {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)) // Rounded corners
                .background(Color(0x1AFFFFFF))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = null,
                            modifier = Modifier.size(120.dp)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Tomorrow", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "H: ${tomorrow.day.maxtempC.toInt()}°", fontSize = 40.sp, fontWeight = FontWeight.Bold)
                        Text(text = "L: ${tomorrow.day.mintempC.toInt()}°", fontSize = 40.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Mostly Cloudy", fontSize = 15.sp)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_precipitation),
                            contentDescription = null,
                            modifier = Modifier.width(40.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "22%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Rain", fontSize = 15.sp)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_wind),
                            contentDescription = null,
                            modifier = Modifier.width(40.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "12 km/h", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Wind speed", fontSize = 15.sp)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = null,
                            modifier = Modifier.width(40.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "18%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Humidity", fontSize = 15.sp)
                    }
                }
            }
        }
        Box(modifier = Modifier.padding(10.dp)) {
            LazyColumn {
                items(forecast.forecast.forecastday) { forecastByDay ->
                    EachDayWeatherCard(forecastByDay)
                }
            }
        }
    }
}


@Composable
fun EachDayWeatherCard(forecastByDay: Forecastday){
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x1AFFFFFF)),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(color = Color.Red),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_precipitation),
                        contentDescription = null,
                        modifier = Modifier.width(40.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "${forecastByDay.day.dailyChanceOfRain.toInt()}%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Rain", fontSize = 15.sp)
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wind),
                        contentDescription = null,
                        modifier = Modifier.width(40.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "${forecastByDay.day.maxwindKph.toInt()} km/h", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Wind speed", fontSize = 15.sp)
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_humidity),
                        contentDescription = null,
                        modifier = Modifier.width(40.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "${forecastByDay.day.avghumidity.toInt()}%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Humidity", fontSize = 15.sp)
                }

            }
        }
    }
}