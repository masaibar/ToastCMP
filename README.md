# CMPToaster

[![build](https://github.com/masaibar/CMPToaster/actions/workflows/build.yml/badge.svg)](https://github.com/masaibar/CMPToaster/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.masaibar/cmp-toaster.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.masaibar/cmp-toaster)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-blue.svg?logo=kotlin)
![Platforms](https://img.shields.io/badge/Platforms-Android%20%7C%20iOS-brightgreen.svg)

A tiny **Kotlin Multiplatform (KMP)** toast library for **Compose Multiplatform** (Android + iOS).

iOS has no native toast. This library fills that gap: on iOS it renders a transient
overlay in a dedicated `UIWindow` above normal app content, with the window configured
not to receive touch events. On Android it delegates to the native
`android.widget.Toast`.

- ✅ One API, two platforms
- ✅ No non-Compose runtime dependencies
- ✅ Platform-backed presentation

## Why CMPToaster?

Some KMP toast implementations **draw** the toast inside the Compose tree
(a `Popup`/overlay). That ties the toast to the composition: it may be covered by native
dialogs, and navigation behavior depends on where the overlay is hosted.

CMPToaster takes a different route. The API is Compose-friendly (`rememberToast()`), but the
toast itself is rendered **natively**:

- **iOS** — presented on a dedicated `UIWindow` at `UIWindowLevelAlert + 1`. So it:
  - can remain visible during common Compose navigation and UIKit transitions
  - is intended to appear above Compose dialogs and standard UIKit alerts / action sheets
  - does not need a Compose host for the toast overlay
- **Android** — delegates to the native `android.widget.Toast`.

In short: a Kotlin Multiplatform toast with a Compose-facing API and platform-backed presentation.

> Note: the iOS overlay is positioned near the bottom safe area and is not intended
> to cover the software keyboard. Android keyboard behavior is handled by the native
> platform toast.

## Install

CMPToaster is published to Maven Central. Make sure `mavenCentral()` is in your
repositories, then add the dependency to your shared module.

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}
```

```kotlin
// build.gradle.kts (your shared module)
kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation("com.masaibar:cmp-toaster:1.0.0")
    }
  }
}
```

## Usage

```kotlin
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.masaibar.cmptoaster.ToastDuration
import com.masaibar.cmptoaster.rememberToast

@Composable
fun Demo() {
  val toast = rememberToast()

  Button(onClick = { toast.show("This is a short toast") }) {
    Text("Short toast")
  }

  Button(onClick = { toast.show("This is a long toast", ToastDuration.LONG) }) {
    Text("Long toast")
  }
}
```

That's the whole API:

```kotlin
expect class Toast {
  fun show(
    message: String,
    duration: ToastDuration = ToastDuration.SHORT
  )
}

enum class ToastDuration { SHORT, LONG }

@Composable
expect fun rememberToast(): Toast
```

| Duration | Android        | iOS   |
|----------|----------------|-------|
| `SHORT`  | `LENGTH_SHORT` | ~2.0s |
| `LONG`   | `LENGTH_LONG`  | ~3.5s |

## Requirements

To build the library and run the sample:

| Tool | Version |
|------|---------|
| JDK | 17 |
| Gradle | 8.11.1 (bundled via wrapper) |
| Android Gradle Plugin | 8.7.3 |
| Android Studio | Ladybug (2024.2.1) |
| Kotlin | 2.1.21 |
| Compose Multiplatform | 1.8.2 |
| Android `compileSdk` / `minSdk` | 35 / 29 |
| Xcode (for iOS) | 16.x |
| iOS deployment target | 15.0 |

> The published artifact was built with Kotlin 2.1.21 and Compose Multiplatform 1.8.2.
> Consumer projects should use compatible Kotlin and Compose Multiplatform versions.

## Sample app

| Android | iOS |
|:---:|:---:|
| <img src="assets/sample-android.gif" alt="CMPToaster sample on Android" width="240"> | <img src="assets/sample-ios.gif" alt="CMPToaster sample on iOS" width="240"> |

A runnable Compose Multiplatform sample lives in [`sample/`](sample) (shared UI), with an
Android app and an iOS app in [`iosApp/`](iosApp). It includes:

- **Basics** — short and long toasts
- **Dialog example** — show a toast while a Compose `AlertDialog` is open
- **Navigation example** — show a toast, then move to another screen

Run it:

- **Android** — open the repository root in Android Studio and run the `sample` configuration
- **iOS** — open `iosApp/iosApp.xcodeproj` in Xcode and run on a simulator

## Supported targets

- `android` (minSdk 29)
- `iosArm64`, `iosSimulatorArm64`

## Building

```bash
./gradlew :toast:assemble                          # Android
./gradlew :toast:compileKotlinIosSimulatorArm64    # iOS (fast check)
```

## License

[MIT](LICENSE) © masaibar
