# ToastCMP

[![build](https://github.com/masaibar/ToastCMP/actions/workflows/build.yml/badge.svg)](https://github.com/masaibar/ToastCMP/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.masaibar/toast-cmp.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.masaibar/toast-cmp)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-blue.svg?logo=kotlin)
![Platforms](https://img.shields.io/badge/Platforms-Android%20%7C%20iOS-brightgreen.svg)

A tiny **Android-style toast for Compose Multiplatform** (Android + iOS).

iOS has no native toast. This library fills that gap: on iOS it renders a transient
overlay on a dedicated `UIWindow` above your key window — so it survives screen
transitions and never blocks touches — while on Android it simply delegates to the
native `android.widget.Toast`.

- ✅ One API, two platforms
- ✅ Zero third-party dependencies (only Compose runtime)
- ✅ Safe to call from any thread

## Why ToastCMP?

Most Compose Multiplatform toast libraries **draw** the toast inside the Compose tree
(a `Popup`/overlay). That ties the toast to the composition: it can be covered by native
dialogs, and it only survives navigation if you host it at the app root.

ToastCMP takes a different route. The API is Compose-friendly (`rememberToast()`), but the
toast itself is rendered **natively**:

- **iOS** — presented on a dedicated `UIWindow` above `UIWindowLevelAlert`. So it:
  - survives every transition — Compose navigation, `UIViewController` push/present, even native modals
  - floats above native alerts, action sheets, and the keyboard
  - needs no Compose host and pulls in zero third-party dependencies
- **Android** — delegates to the native `android.widget.Toast`, which already behaves this way.

In short: a Compose API, with native behavior that "just shows" — anywhere, on top of anything.

## Install

```kotlin
// settings.gradle.kts — mavenCentral() already covers it
// build.gradle.kts (your shared module)
kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation("io.github.masaibar:toast-cmp:0.1.0")
    }
  }
}
```

> Not yet published to Maven Central. For now, consume it via
> [composite build](https://docs.gradle.org/current/userguide/composite_builds.html)
> or copy the `toast/` module into your project.

## Usage

```kotlin
import io.github.masaibar.toastcmp.ToastDuration
import io.github.masaibar.toastcmp.rememberToast

@Composable
fun MyScreen() {
  val toast = rememberToast()

  Button(onClick = { toast.show("Saved!") }) {
    Text("Save")
  }

  Button(onClick = { toast.show("Upload finished", ToastDuration.LONG) }) {
    Text("Upload")
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
