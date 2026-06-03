# CMPToaster

[![build](https://github.com/masaibar/CMPToaster/actions/workflows/build.yml/badge.svg)](https://github.com/masaibar/CMPToaster/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.masaibar/cmp-toaster.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.masaibar/cmp-toaster)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-blue.svg?logo=kotlin)
![Platforms](https://img.shields.io/badge/Platforms-Android%20%7C%20iOS-brightgreen.svg)

A tiny **Android-style toast for Compose Multiplatform** (Android + iOS).

iOS has no native toast. This library fills that gap: on iOS it renders a transient
overlay on a dedicated `UIWindow` above your key window — so it survives screen
transitions and never blocks touches — while on Android it simply delegates to the
native `android.widget.Toast`.

- ✅ One API, two platforms
- ✅ Zero third-party dependencies (only Compose runtime)
- ✅ Safe to call from any thread

## Why CMPToaster?

Most Compose Multiplatform toast libraries **draw** the toast inside the Compose tree
(a `Popup`/overlay). That ties the toast to the composition: it can be covered by native
dialogs, and it only survives navigation if you host it at the app root.

CMPToaster takes a different route. The API is Compose-friendly (`rememberToast()`), but the
toast itself is rendered **natively**:

- **iOS** — presented on a dedicated `UIWindow` at `UIWindowLevelAlert + 1`. So it:
  - survives every transition — Compose navigation, `UIViewController` push/present, even native modals
  - floats above Compose dialogs and native alerts / action sheets
  - needs no Compose host and pulls in zero third-party dependencies
- **Android** — delegates to the native `android.widget.Toast`, which already behaves this way.

In short: a Compose API, with native behavior that "just shows" — on top of your dialogs, across navigation.

> Note: like any toast, it does not draw over the software keyboard (the keyboard window
> sits at a higher level on both platforms).

## Install

```kotlin
// settings.gradle.kts — mavenCentral() already covers it
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
  fun show(message: String, duration: ToastDuration = ToastDuration.SHORT)
}

enum class ToastDuration { SHORT, LONG }

@Composable
expect fun rememberToast(): Toast
```

| Duration | Android        | iOS   |
|----------|----------------|-------|
| `SHORT`  | `LENGTH_SHORT` | ~2.0s |
| `LONG`   | `LENGTH_LONG`  | ~3.5s |

## Sample app

A runnable Compose Multiplatform sample lives in [`sample/`](sample) (shared UI), with an
Android app and an iOS app in [`iosApp/`](iosApp). It demonstrates where a native toast
shines:

- **Basics** — short and long toasts
- **Shows above a dialog** — the toast appears on top of a Compose `AlertDialog`
- **Survives navigation** — show a toast, move to another screen, and it stays visible

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

## Acknowledgements

The logo includes the Android robot. The Android robot is reproduced or modified from
work created and shared by Google and used according to terms described in the
[Creative Commons 3.0 Attribution License](https://creativecommons.org/licenses/by/3.0/).

## License

[MIT](LICENSE) © masaibar
