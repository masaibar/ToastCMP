# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.0]

### Added

- Initial release.
- `Toast` with `show(message, duration)` and `rememberToast()` for Compose Multiplatform.
- Android implementation backed by the native `android.widget.Toast`.
- iOS implementation rendering a transient overlay on a dedicated `UIWindow`
  that survives screen transitions and never blocks touches.
- `ToastDuration` (`SHORT` / `LONG`).

[Unreleased]: https://github.com/masaibar/ToastCMP/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/masaibar/ToastCMP/releases/tag/v0.1.0
