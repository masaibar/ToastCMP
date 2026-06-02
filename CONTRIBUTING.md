# Contributing

Thanks for your interest in improving `ToastCMP`! Contributions of
all sizes are welcome — bug reports, docs, and code.

## Getting started

```bash
git clone https://github.com/masaibar/ToastCMP.git
cd ToastCMP
./gradlew :toast:assemble                        # Android library
./gradlew :toast:compileKotlinIosSimulatorArm64  # iOS compile check
./gradlew :toast:iosSimulatorArm64Test           # run tests (iOS)
```

## Pull requests

1. Fork the repo and create a branch from `main` (`feat/...`, `fix/...`, `docs/...`).
2. Keep changes focused and small. One logical change per PR.
3. Make sure the build and tests pass before opening the PR.
4. Fill in the PR template. Describe **what** changed and **why**.

CodeRabbit reviews every PR automatically — feel free to reply to its comments.

## Reporting bugs / requesting features

Open an issue using the templates. Include the platform (Android / iOS), library
version, and a minimal reproduction when possible.

## Code style

- Kotlin official code style (2-space indent).
- Public API changes should update the KDoc and the README.

## License

By contributing, you agree that your contributions are licensed under the
[MIT License](LICENSE).
