# GitHub User Search

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/kotlin-latest-blue.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-latest-brightgreen.svg)](https://developer.android.com/jetpack/compose)

This application consumes the GitHub API to fetch user profiles from the platform. It features a dynamic search functionality and profile details for navigation. The app locally stores data downloaded from the API in a Room database so it can still be viewed without an Internet connection.

## ✨ Key Features

- **User Search**: Filter GitHub users in real-time
- **Profile Details**: View comprehensive GitHub user profile information
- **Offline Mode**: Access previously downloaded data without internet connection
- **Modern Interface**: Built with Jetpack Compose for a smooth experience

## 🏗️ Architecture

This application follows the MVVM (Model-View-ViewModel) architecture and modern Android development practices.

## 🧰 Tech Stack

- **Minimum SDK**: Level 24
- **Language**: Kotlin with Coroutines + Flow for asynchronous operations
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM

## 🗃️ Libraries

- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**: Screen navigation
- **[Dagger Hilt](https://dagger.dev/hilt/)**: Dependency injection
- **[Retrofit](https://square.github.io/retrofit/)**: HTTP client for API consumption
- **[Landscapist](https://github.com/skydoves/landscapist)**: Image loading and caching
- **[Room](https://developer.android.com/training/data-storage/room)**: Local database for offline storage

## 🚀 Getting Started

### Prerequisites
- Latest version of Android Studio
- Android SDK 24 or higher
- Latest version of Kotlin

### Installation
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle and run on an emulator or physical device
