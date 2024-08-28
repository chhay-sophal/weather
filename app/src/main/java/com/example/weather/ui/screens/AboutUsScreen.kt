package com.example.weather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weather.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun AboutUsScreen(
    navController: NavController = rememberNavController()
) {
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
                title = { Text(text = "About Us") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App Logo with rounded corners
                Image(
                    painter = painterResource(id = R.drawable.weather_app_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(50))
                        .size(100.dp) // Adjust size as needed
                )

                // App Title
                Text(
                    text = "Welcome to Weather App!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA726),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Description Section
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1C1C1C),
                        contentColor = Color(0xFFE5E5E5)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .shadow(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Weather app provides you with real-time weather updates, forecasts, and alerts, " +
                                    "ensuring you are always prepared for any weather conditions. Our easy-to-use interface " +
                                    "allows you to view the weather for your current location or any location worldwide.",
                            fontSize = 16.sp,
                            color = Color(0xFFE5E5E5),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                // Features Section
                Text(
                    text = "Key Features:",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA726),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1C1C1C),
                        contentColor = Color(0xFFE5E5E5)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .shadow(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "- Real-time weather data and updates\n" +
                                    "- Detailed forecasts for the next 7 days\n" +
                                    "- Severe weather alerts and notifications\n" +
                                    "- Easy-to-understand weather maps and radar",
                            fontSize = 16.sp,
                            color = Color(0xFFE5E5E5),
                            textAlign = TextAlign.Left
                        )
                    }
                }

                // Mission Statement
                Text(
                    text = "Our Mission",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA726),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1C1C1C),
                        contentColor = Color(0xFFE5E5E5)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .shadow(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "At Weather, our mission is to keep you informed about the weather, no matter where you are. " +
                                    "We believe in providing accurate, timely, and reliable weather information to help you make " +
                                    "the best decisions for your day.",
                            fontSize = 16.sp,
                            color = Color(0xFFE5E5E5),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}