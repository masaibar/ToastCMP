# Repository Guidelines

## Project Structure & Module Organization

CMPToaster is a small Kotlin Multiplatform library with one Gradle module, `:toast`.

- `toast/src/commonMain/kotlin/com/masaibar/cmptoaster/` contains the shared public API (`Toast`, `ToastDuration`, `rememberToast`).
- `toast/src/androidMain/kotlin/...` contains Android-specific implementations that delegate to `android.widget.Toast`.
- `toast/src/iosMain/kotlin/...` contains iOS-specific native overlay behavior.
- `toast/src/androidUnitTest/kotlin/...` contains JVM/Robolectric unit tests.
- Root files such as `README.md`, `CHANGELOG.md`, `SECURITY.md`, and `CONTRIBUTING.md` document usage, releases, security policy, and contribution flow.

## Build, Test, and Development Commands

Use the Gradle wrapper and Java 17.

- `./gradlew :toast:assemble` builds the Android library artifact.
- `./gradlew :toast:testDebugUnitTest` runs Android unit tests with JUnit and Robolectric.
- `./gradlew :toast:compileKotlinIosSimulatorArm64` performs the fast iOS simulator compile check.

Before opening a PR, run the relevant command for the platform you touched; for shared API changes, run all three.

## Coding Style & Naming Conventions

Use Kotlin official style with 2-space indentation, matching the existing Gradle and Kotlin files. Keep APIs minimal and platform behavior behind `expect`/`actual` declarations. Public types use `PascalCase`; functions, properties, and test methods use `camelCase`. Keep package paths under `com.masaibar.cmptoaster`.

Avoid new third-party dependencies unless they are necessary for the library API or tests. Public API changes should update KDoc and `README.md` examples.

## Testing Guidelines

Tests currently use JUnit 4 and Robolectric in `androidUnitTest`. Name test files after the behavior or type under test, for example `AndroidToastTest.kt`. Add or update tests when behavior changes, especially around threading, duration mapping, and Android toast delegation. For iOS changes, run the iOS compile task at minimum because there is no simulator test job in CI.

## Commit & Pull Request Guidelines

Commit history uses short conventional prefixes such as `feat:` and `ci:`. Prefer concise messages like `fix: handle empty toast messages` or `docs: update install instructions`.

Create branches from `main` using `feat/...`, `fix/...`, or `docs/...`. PRs should stay focused, explain what changed and why, link issues with `Closes #123` when applicable, and complete the checklist in `.github/PULL_REQUEST_TEMPLATE.md`. Include screenshots or recordings only when a visual behavior change needs review.

## Security & Configuration Tips

Do not commit local build outputs, credentials, signing material, or generated logs. Report vulnerabilities according to `SECURITY.md`.
