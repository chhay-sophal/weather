package com.example.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weather.viewmodel.WeatherViewModel

@Preview(showSystemUi = true)
@Composable
fun SearchScreen(
    navController: NavController = rememberNavController(),
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val searchText by weatherViewModel.searchText.collectAsState()
    val searchResults by weatherViewModel.searchLocation.observeAsState(emptyList())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF050D22),
        contentColor = Color(0xFFE5E5E5),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { weatherViewModel.onSearchTextChange(it) },
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(end = 0.dp)
                        .weight(1f)
                        .background(Color(0x1AFFFFFF), shape = RoundedCornerShape(100f)),
                    placeholder = { Text(text = "Search...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedContainerColor = Color(0x1AFFFFFF),
                        unfocusedContainerColor = Color(0x1AFFFFFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLeadingIconColor = Color.White,
                        unfocusedLeadingIconColor = Color.White,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(100f)
                )

                Text(
                    text = "Cancel",
                    modifier = Modifier
                        .clickable(
                            indication = null, // Disables ripple effect
                            interactionSource = remember { MutableInteractionSource() } // Required to handle interactions
                        ) {
                            navController.popBackStack()
                        }
                        .wrapContentWidth()
                        .padding(end = 10.dp),
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn {
                items(searchResults) {result ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 30.dp)
                        ) {
                            Text(text = "${result.name}, ${result.country}")
                        }
                    }
                }
            }
        }
    }
}
