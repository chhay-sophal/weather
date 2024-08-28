package com.example.weather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.R
import com.example.weather.R.drawable.heat
import com.example.weather.R.drawable.precipitation
import com.example.weather.R.drawable.pressure
import com.example.weather.R.drawable.sunrise
import com.example.weather.R.drawable.sunshine
import com.example.weather.R.drawable.temperature
import com.example.weather.R.drawable.visibility
import com.example.weather.R.drawable.waxing_moon
import com.example.weather.data.models.WeatherRoot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UVIndexBox(
    weather: WeatherRoot
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Today", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE1C91C))
            Text(text = "Next 7 days", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }

        Box {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(weather.forecast.forecastday[0].hour) { hour ->
                    Box(
                        modifier = Modifier
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
                            Text(text = "${hour.tempC.toInt()}°", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

//      Forecast
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Forecast", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE1C91C))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Column(
                    Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = sunshine), contentDescription = null, Modifier.width(15.dp))
                        Text(text = "UV INDEX")
                    }
                    val uvIndex = weather.current.uv.toInt()
                    val uvStatus = when {
                        uvIndex < 3 -> "Low"
                        uvIndex in 3..5 -> "Moderate"
                        uvIndex in 6..7 -> "High"
                        uvIndex in 8..10 -> "Very High"
                        else -> "Extreme"
                    }
                    Text(text = uvIndex.toString(), fontSize = 30.sp)
                    Text(text = uvStatus, fontSize = 20.sp)
                }
            }
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Column(
                    Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = temperature), contentDescription = null, Modifier.width(15.dp))
                        Text(text = "FEEL LIKE")
                    }
                    Text(text = "${weather.current.feelslikeC.toInt()}°", fontSize = 30.sp)
                    Text(text = "Sunset: ${weather.forecast.forecastday[0].astro.sunset}", fontSize = 15.sp)
                }
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color(0x1AFFFFFF),
            contentColor = Color.White,
            shape = RoundedCornerShape(13.dp)
        ) {
            Column(
                Modifier.padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = temperature), contentDescription = null, Modifier.width(15.dp))
                    Text(text = "WIND")
                }
                Row {
                    Column(
                        modifier = Modifier
                            .weight(.6f),
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Space between rows
                    ) {
                        // First Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier.width(60.dp) // Width to accommodate two digits
                            ) {
                                Text(
                                    text = "${weather.current.windKph.toInt()}",
                                    fontSize = 45.sp,
                                    modifier = Modifier.align(Alignment.CenterEnd) // Align text to the right
                                )
                            }

                            Column {
                                Text(text = "KM/H", fontSize = 10.sp)
                                Text(text = "Wind", fontSize = 16.sp)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier.width(60.dp) // Width to accommodate two digits
                            ) {
                                Text(
                                    text = "${weather.current.gustKph.toInt()}",
                                    fontSize = 45.sp,
                                    modifier = Modifier.align(Alignment.CenterEnd) // Align text to the right
                                )
                            }

                            Column {
                                Text(text = "KM/H", fontSize = 10.sp)
                                Text(text = "Gusts", fontSize = 16.sp)
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val directionAngles = mapOf(
                            "N" to 0f,
                            "NNE" to 22.5f,
                            "NE" to 45f,
                            "ENE" to 67.5f,
                            "E" to 90f,
                            "ESE" to 112.5f,
                            "SE" to 135f,
                            "SSE" to 157.5f,
                            "S" to 180f,
                            "SSW" to 202.5f,
                            "SW" to 225f,
                            "WSW" to 247.5f,
                            "W" to 270f,
                            "WNW" to 292.5f,
                            "NW" to 315f,
                            "NNW" to 337.5f
                        )

                        val rotationAngle = directionAngles[weather.current.windDir] ?: 0f

                        // Compass Circle
                        Surface(
                            modifier = Modifier.size(115.dp),
                            color = Color.Gray.copy(alpha = 0.1f),
                            shape = CircleShape
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                // Directions
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(text = "N", fontSize = 20.sp, color = Color.Gray)
                                    Spacer(modifier = Modifier.weight(1f)) // Push N to the top
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = "W", fontSize = 20.sp, color = Color.Gray)
                                        Text(text = "E", fontSize = 20.sp, color = Color.Gray)
                                    }
                                    Spacer(modifier = Modifier.weight(1f)) // Push S to the bottom
                                    Text(text = "S", fontSize = 20.sp, color = Color.Gray)
                                }
                            }
                        }
                        // Arrow icon (rotated based on the wind direction)
                        Icon(
                            imageVector = Icons.Filled.ArrowUpward, // Replace with your arrow vector asset
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                                .graphicsLayer(rotationZ = rotationAngle) // Rotate based on wind direction
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Column(
                    Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = precipitation), contentDescription = null, Modifier.width(15.dp))
                        Text(text = "PRECIPITATION")
                    }
                    Text(text = "${weather.current.precipMm.toInt()} mm", fontSize = 30.sp)
                }
            }
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Column(
                    Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = visibility), contentDescription = null, Modifier.width(15.dp))
                        Text(text = "VISIBILITY")
                    }
                    Text(text = "${weather.current.visKm.toInt()} km", fontSize = 30.sp)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Column(
                    Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = pressure), contentDescription = null, Modifier.width(15.dp))
                        Text(text = "PRESSURE")
                    }
                    Text(text = "${weather.current.pressureMb.toInt()} mb", fontSize = 30.sp)
                }
            }
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Column(
                    Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = heat), contentDescription = null, Modifier.width(15.dp))
                        Text(text = "HEAT INDEX")
                    }
                    Text(text = "${weather.current.heatindexC.toInt()}°", fontSize = 30.sp)
                }
            }
        }

//      Astronomy
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Astronomy", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE1C91C))
        }
        Row {
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Row(modifier = Modifier.padding(15.dp), horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    Column(
                        Modifier.weight(.7f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = sunrise), contentDescription = null, Modifier.width(15.dp))
                            Text(text = "SUNRISE")
                        }
                        Text(text = weather.forecast.forecastday[0].astro.sunrise, fontSize = 30.sp)
                        Text(text = "Sunset: ${weather.forecast.forecastday[0].astro.sunset}", fontSize = 15.sp)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.sun_image), // Replace with your image resource
                        contentDescription = null,
                        modifier = Modifier
                            .size(110.dp)
                            .weight(.3f)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Row {
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = Color(0x1AFFFFFF),
                contentColor = Color.White,
                shape = RoundedCornerShape(13.dp)
            ) {
                Row(modifier = Modifier.padding(15.dp), horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.moon_image), // Replace with your image resource
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(.3f)
                            .align(Alignment.CenterVertically),
                    )
                    Column(
                        Modifier.weight(.7f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = waxing_moon), contentDescription = null, Modifier.width(15.dp))
                            Text(text = "MOON")
                        }
                        Row {
                            Text(text = "Illumination:", fontSize = 15.sp)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "${weather.forecast.forecastday[0].astro.moonIllumination}%", fontSize = 15.sp)
                        }
                        Row {
                            Text(text = "Phase:", fontSize = 15.sp)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = weather.forecast.forecastday[0].astro.moonPhase, fontSize = 15.sp)
                        }
                        Row {
                            Text(text = "Moonrise:", fontSize = 15.sp)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = weather.forecast.forecastday[0].astro.moonrise, fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}
