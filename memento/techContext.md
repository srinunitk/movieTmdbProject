# Technical Context

## Core Technologies:
- **Programming Language**: Kotlin
- **Platform**: Android
- **UI Toolkit**: Jetpack Compose

## Testing Frameworks:
- **UI Testing**: UI Automator (`androidx.test.uiautomator`)
- **Test Runner**: AndroidJUnitRunner
- **Assertions**: JUnit (`junit:junit`, `androidx.test.ext:junit`)

## Build System:
- Gradle

## Key Dependencies (to be added for UI testing):
- `androidx.compose.ui:ui-test-junit4`
- `androidx.test.uiautomator:uiautomator`
- `androidx.test.ext:junit`
- `androidx.test:runner`
- `androidx.test:rules`
- `androidx.compose.material3:material3` (or relevant Compose Material version)
- `androidx.activity:activity-compose`

## Development Setup:
- Android Studio (or compatible IDE like VS Code with Android extensions)
- Android SDK

## Technical Constraints:
- Tests should target the app package `com.example.movieapp`.
- (Further constraints to be documented)
