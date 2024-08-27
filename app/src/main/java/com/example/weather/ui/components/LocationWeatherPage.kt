package com.example.weather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.R
import com.example.weather.data.models.WeatherRoot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LocationWeatherPage(forecast: WeatherRoot) {
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
