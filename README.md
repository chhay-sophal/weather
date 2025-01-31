# Weather App

Welcome to the **Weather App**, an Android application designed to provide real-time weather updates using the [WeatherAPI](https://www.weatherapi.com/). This app is built with modern Android development tools, including Jetpack Compose, Kotlin Coroutines, and other libraries to ensure a smooth and responsive user experience.

## Features

- **Real-Time Weather Updates**: Get the latest weather information for your location or any city worldwide.
- **User-Friendly Interface**: Built with Jetpack Compose for a modern and intuitive UI.
- **Location-Based Weather**: Automatically detects your location and provides weather updates.
- **Search Functionality**: Search for weather information in any city.
- **Detailed Weather Information**: Includes temperature, humidity, wind speed, and more.
- **Material Design**: Follows Material Design 3 guidelines for a cohesive and beautiful design.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio** (latest version recommended)
- **JDK 8 or higher**
- **Android SDK** (API level 24 or higher)
- **WeatherAPI Key**: You need an API key from [WeatherAPI](https://www.weatherapi.com/) to fetch weather data.

## Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/chhay-sophal/weather-app.git
   cd weather-app
   ```

2. **Add Your WeatherAPI Key**:
   - Open the project in Android Studio.
   - Create a `local.properties` file in the root directory if it doesn't already exist.
   - Add your WeatherAPI key to the `local.properties` file:
     ```properties
     WEATHER_API_KEY=your_api_key_here
     ```

3. **Build the Project**:
   - Open the project in Android Studio.
   - Sync the project with Gradle files.
   - Build and run the project on an emulator or a physical device.

## Dependencies

The project uses the following dependencies:

- **Jetpack Compose**: For building the UI.
- **Kotlin Coroutines**: For asynchronous programming.
- **Retrofit**: For making network requests to the WeatherAPI.
- **Coil**: For image loading.
- **Accompanist**: For handling permissions.
- **Google Play Services**: For location services.

For a complete list of dependencies, refer to the `build.gradle.kts` file.

## Project Structure

The project is structured as follows:

- **`app/src/main/java/com/example/weather`**: Contains the main application code.
  - **`data`**: Contains the Retrofit service and data models.
  - **`ui`**: Contains the UI components and screens.
  - **`viewmodel`**: Contains the ViewModel classes for managing UI-related data.
- **`app/src/main/res`**: Contains resources such as layouts, drawables, and strings.
- **`app/src/test`**: Contains unit tests.
- **`app/src/androidTest`**: Contains instrumented tests.

## Running Tests

To run the unit tests, use the following command:

```bash
./gradlew test
```

To run the instrumented tests on an Android device or emulator, use:

```bash
./gradlew connectedAndroidTest
```

## Acknowledgments

- [WeatherAPI](https://www.weatherapi.com/) for providing the weather data.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the modern UI toolkit.
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming.

---

Thank you for checkout the Weather App! Stay updated with the latest weather information wherever you are. 🌤️🌧️🌩️
