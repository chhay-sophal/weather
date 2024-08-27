package com.example.weather.ui.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.weather.data.models.WeatherRoot

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