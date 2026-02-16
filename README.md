# CityViewer

<img src="https://via.placeholder.com/800x400.png?text=CityViewer+Screenshot" alt="CityViewer main screen" width="800"/>

**CityViewer** is an Android application for viewing and exploring urban spaces.

(Description currently missing — please add what the app actually does)

## Features

- Viewing 3D building models / city layouts
- Real-time city map navigation
- Support for various data sources (OSM, custom models, etc.)
- Display modes: day/night, wireframe, density heatmap, and more
- (Add your real features here)

## Tech Stack

- **Language**: Java
- **Platform**: Android
- **Build**: Gradle
- **Minimum SDK**: 24 (Android 7.0) — can be adjusted
- Libraries in use (example — please update with actual ones):
  - OpenGL ES / Sceneform / Filament / ...
  - Google Maps SDK / Mapbox / OSMAnd core / ...
  - Kotlin coroutines / LiveData / Jetpack (if applicable)

## Installation & Running

### Option 1 — Recommended (via Android Studio)

1. Clone the repository
   ```bash
   git clone https://github.com/bask0xff/CityViewer.git
   ```
   
2. Open the project in Android Studio
3. Wait for Gradle sync to complete
4. Run the app on an emulator or physical device

### Option 2 — Build APK from terminal

```bash
./gradlew assembleDebug
```

### Project Structure
```
CityViewer/
├── app/                    # Main Android application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/cityviewer/     ← Core source code
│   │   │   ├── res/                                 ← Resources
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── gradle/                 # Gradle wrapper
├── .gitignore
├── build.gradle            # Root build file
├── settings.gradle
└── README.md
```

### Roadmap / TODO

 Integrate real geodata source (OSM, GeoJSON, 3D Tiles…)
 Add support for 3D building models
 Implement address / POI search
 Add AR mode (if planned)
 Dark theme + settings
 Performance optimization for low-end devices
 Code documentation + architecture (MVVM / Clean Architecture / …)

### License
MIT License (or replace with your preferred license)

The project is in a very early stage of development.
Contributions, ideas, and feedback are welcome!









