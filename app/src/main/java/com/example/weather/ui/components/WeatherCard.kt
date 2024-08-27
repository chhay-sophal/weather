package com.example.weather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.data.models.WeatherRoot

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