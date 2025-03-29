# GitHub User Search

![Platform](https://img.shields.io/badge/platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/kotlin-latest-blue.svg)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-latest-brightgreen.svg)

This application consumes the GitHub API to fetch user profiles from the platform. It features a dynamic search functionality and profile details for navigation. The app locally stores data downloaded from the API in a Room database so it can still be viewed without an Internet connection.

## ✨ Key Features

- **User Search**: Filter GitHub users in real-time using a search bar.
- **Profile Details**: View comprehensive GitHub user profile information, including repositories and followers.
- **Offline Mode**: Access previously downloaded data without an internet connection.
- **Modern Interface**: Built with Jetpack Compose for a smooth and interactive UI.
- **Error Handling**: Graceful handling of network failures and empty states.
- **Pagination Support**: Efficiently loads large datasets using paging.

## 🏗️ Architecture

This application follows the MVVM (Model-View-ViewModel) architecture to ensure modularity, scalability, and maintainability. The key reasons for choosing MVVM include:

- **Separation of concerns**: UI logic is separate from business logic.
- **Improved testability**: ViewModel can be tested independently of the UI.
- **Better lifecycle awareness**: ViewModel survives configuration changes.

## 🧰 Tech Stack

- **Minimum SDK**: Level 24
- **Language**: Kotlin with Coroutines + Flow for asynchronous operations
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM

## 🗃️ Libraries

- [**Navigation Compose**](https://developer.android.com/jetpack/compose/navigation): Handles screen navigation.
- [**Dagger Hilt**](https://dagger.dev/hilt/): Manages dependency injection.
- [**Retrofit**](https://square.github.io/retrofit/): Handles API requests.
- [**OkHttp**](https://square.github.io/okhttp/): Enhances networking capabilities with interceptors.
- [**Kotlinx Serialization**](https://github.com/Kotlin/kotlinx.serialization): JSON parsing.
- [**Landscapist**](https://github.com/skydoves/landscapist): Efficient image loading and caching.
- [**Room**](https://developer.android.com/training/data-storage/room): Provides local database support.
- [**Paging 3**](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): Handles efficient pagination.

## 🚀 Getting Started

### Prerequisites

- Latest version of Android Studio
- Android SDK 24 or higher
- Git installed on your machine

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/github-user-search.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle by clicking `Sync Now` when prompted.
4. Run the project on an emulator or physical device.

### Build & Run

- Use the `Run` button in Android Studio to launch the app.
- To build an APK, execute:
  ```sh
  ./gradlew assembleDebug
  ```
- To run tests:
  ```sh
  ./gradlew test
  ```

## 🚧 Challenges & Improvements

### Challenges Faced

- Efficiently handling large datasets required implementing the Paging 3 library.
- Managing network errors gracefully to enhance user experience.
- Handling API rate limits imposed by GitHub.

### Future Improvements

- Implementing dark mode support.
- Adding support for user repositories and starred repositories.
- Enhancing search with filters (e.g., language, followers count).

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙌 Contributions

Contributions are welcome! Feel free to open an issue or submit a pull request.
