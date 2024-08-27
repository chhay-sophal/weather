package com.example.weather.viewmodel

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.models.SavedLocation
import com.example.weather.data.models.SearchLocation
import com.example.weather.data.models.WeatherRoot
import com.example.weather.data.services.WeatherService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeatherViewModel() : ViewModel() {
    private val _todayForecast = MutableLiveData<List<WeatherRoot>>()
    val todayForecast: LiveData<List<WeatherRoot>> get() = _todayForecast

    private val _locationForecast = MutableLiveData<WeatherRoot>()
    val locationForecast: LiveData<WeatherRoot> get() = _locationForecast

//    private val _todayForecast = mutableStateOf<Root?>(null)
//    val todayForecast: State<Root?> = _todayForecast

    private val _sevenDayForecast = MutableLiveData<WeatherRoot>()
    val sevenDayForecast: LiveData<WeatherRoot> get() = _sevenDayForecast

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchLocation = MutableLiveData<List<SearchLocation>>()
    val searchLocation: LiveData<List<SearchLocation>> get() = _searchLocation

//    private val _savedLocation = MutableLiveData<List<SearchLocation>> ()
//    val savedSearchLocation: LiveData<List<SearchLocation>> get() = _savedLocation
//    private val cacheManager = CacheManager(context)
    private val _savedLocation = mutableListOf<SavedLocation>()
    val savedLocation: List<SavedLocation> get() = _savedLocation

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> get() = _location

    fun updateLocation(newLocation: Location?) {
        _location.value = newLocation
    }

    private val apiService = WeatherService.getInstance()

    init {
        // Load saved locations
//        _savedLocation.addAll(cacheManager.getSavedLocations())

        // Debounce search text changes
        viewModelScope.launch {
            _searchText.collectLatest { query ->
                if (query.isNotEmpty()) {
                    delay(300)  // 300ms debounce period
                    searchLocations(query)
                }
            }
        }
    }

    private fun fetchTodayForecast() {
        viewModelScope.launch {
            try {
                if (_savedLocation.isNotEmpty()) {
                    val newForecastList = mutableListOf<WeatherRoot>() // Temporary list to store all responses
                    for (location in _savedLocation) {
                        Log.d("WeatherViewModel", "Fetching today's forecast")

                        val locationQuery = "${location.lat}, ${location.lon}"
                        val response = apiService.getTodayForecast(location = locationQuery)
                        newForecastList.add(response) // Add each response to the temporary list
                    }
                    // Update the LiveData with the new list
                    _todayForecast.value = newForecastList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchForecastByLocation(location: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getTodayForecast(location = location)

                _locationForecast.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun onSearchTextChange(newText: String) {
        _searchText.value = newText
    }

    private fun searchLocations(location: String) {
        viewModelScope.launch {
            try {
                Log.d("WeatherViewModel", "Searching locations")

                // Replace with your API call
                val response = apiService.search(location = location)
                _searchLocation.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getLocationByLatLon(lat: String, lon: String): SearchLocation? {
        return _searchLocation.value?.find {
                it.lat == lat.toDouble() && it.lon == lon.toDouble()
            }
    }

    fun saveLocation(location: SavedLocation): Int {
        _savedLocation.add(location)
        fetchTodayForecast()
        Log.d("SaveLocation", "${_savedLocation.indexOf(location)}")
        return _savedLocation.indexOf(location)
    }

    fun removeLocation(location: SavedLocation) {
        _savedLocation.removeAll { it == location }
        fetchTodayForecast()
        Log.d("Remove", "Removing Location")
//        _todayForecast.value = _todayForecast.value.filter { it != weatherInfo }
//        cacheManager.removeLocation(locationId)
    }

    fun getSavedLocations(): List<SavedLocation> {
        return _savedLocation
//        return cacheManager.getSavedLocations()
    }

//    fun fetchSevenDayForecast(apiKey: String, location: String) {
//        WeatherApi.service.getSevenDayForecast(apiKey, location)
//            .enqueue(object : Callback<Root> {
//                override fun onResponse(call: Call<Root>, response: Response<Root>) {
//                    if (response.isSuccessful) {
//                        _sevenDayForecast.postValue(response.body())
//                    } else {
//                        // Handle error
//                    }
//                }
//
//                override fun onFailure(call: Call<Root>, t: Throwable) {
//                    // Handle failure
//                }
//            })
//    }
}

