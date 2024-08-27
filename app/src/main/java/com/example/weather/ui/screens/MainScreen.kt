package com.example.weather.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.weather.R
import com.example.weather.data.models.WeatherRoot
import com.example.weather.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun MainScreen(
    navController: NavController = rememberNavController(),
    weatherViewModel: WeatherViewModel = viewModel(),
    locationPermissionState: PermissionState,
    indexToShow: Int
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val todayForecast by weatherViewModel.todayForecast.observeAsState(emptyList())
    val pagerState = rememberPagerState(pageCount = { todayForecast.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFF050D22),
        contentColor = Color(0xFFE5E5E5),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF050D22),
                    titleContentColor = Color(0xFFE5E5E5),
                    actionIconContentColor = Color(0xFFE5E5E5),
                    navigationIconContentColor = Color(0xFFE5E5E5)
                ),
                title = {
                    Row(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(pagerState.pageCount) { iteration ->
                            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(7.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("list-view") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "List View"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Localized description"
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
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            if (locationPermissionState.status.isGranted && todayForecast.isNotEmpty()) {
                HorizontalPager(state = pagerState) { page ->
                    LocationWeatherCard(todayForecast[page])
                }

                if (indexToShow < todayForecast.size) {
                    coroutineScope.launch {
                        pagerState.scrollToPage(indexToShow)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LocationWeatherCard(forecast: WeatherRoot) {
    Column {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = forecast.location.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            val localDateTime = forecast.current.lastUpdated
            val dateTime = LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            val dayOfWeek = dateTime.dayOfWeek
            val dayOfMonth = dateTime.dayOfMonth
            val month = dateTime.month
            val formattedDate = "$dayOfWeek $dayOfMonth $month"

            Text(
                text = formattedDate,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https:${forecast.current.condition.icon}",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = forecast.current.condition.text,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${forecast.current.tempC.toInt()}°",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "H: ${forecast.forecast.forecastday[0].day.maxtempC.toInt()}", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(text = "L: ${forecast.forecast.forecastday[0].day.mintempC.toInt()}", fontSize = 20.sp)
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
                        Text(text = "${forecast.forecast.forecastday[0].day.dailyChanceOfRain}%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                        Text(text = "${forecast.current.windKph.toInt()} km/h", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                        Text(text = "${forecast.current.humidity}%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Humidity", fontSize = 15.sp)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Today", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE1C91C))
            Text(text = "Next 7 days", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }

        Box(modifier = Modifier.padding(10.dp)) {
            LazyRow {
                items(forecast.forecast.forecastday[0].hour) { hour ->
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(100.dp, 130.dp)
                            .clip(RoundedCornerShape(13.dp)) // Rounded corners
                            .background(Color(0x1AFFFFFF))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            val date = hour.time
                            val time = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                            val formattedTime = time.format(DateTimeFormatter.ofPattern("h a")).lowercase()

                            Text(text = formattedTime, fontSize = 16.sp)
                            AsyncImage(
                                model = "https:${hour.condition.icon}",
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Text(text = hour.tempC.toInt().toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
