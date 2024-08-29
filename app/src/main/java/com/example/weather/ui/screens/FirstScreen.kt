package com.example.weather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather.R

@Composable
fun FirstScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display an introductory image
                Image(
                    painter = painterResource(id = R.drawable.boy_with_umbrella_in_rain),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Display the title text
                Text(
                    text = "Welcome to Weather",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the subtitle text
                Text(
                    text = "Stay updated with the latest weather information.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Display the "Get Started" button
                Button(
                    onClick = {
                        navController.navigate("choose-location")
                    }
                ) {
                    Text(text = "Get Started", fontSize = 16.sp)
                }
            }
        }
    }
}