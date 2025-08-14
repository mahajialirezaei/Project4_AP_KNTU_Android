# Contributing

Thank you for your interest in contributing to **Project4_AP_KNTU_Android**. This document explains how to report issues, propose changes, and submit pull requests.

## Table of contents

* [Getting started](#getting-started)
* [Build and run](#build-and-run)
* [Project structure](#project-structure)
* [Coding style](#coding-style)
* [Branching and commits](#branching-and-commits)
* [Submitting a pull request](#submitting-a-pull-request)
* [Reporting issues](#reporting-issues)
* [Testing and linting](#testing-and-linting)
* [Code review process](#code-review-process)
* [License and contact](#license-and-contact)

## Getting started

Prerequisites:

* Java Development Kit (JDK) 11 or newer
* Android Studio (recommended) or a compatible IDE
* Android SDK and platform tools installed
* Git

Clone the repository:

```bash
git clone https://github.com/mahajialirezaei/Project4_AP_KNTU_Android.git
cd Project4_AP_KNTU_Android
```

## Build and run

Build with the Gradle wrapper:

```bash
./gradlew assembleDebug
```

Open the project in Android Studio and run on an emulator or connected device.

## Project structure

* `app/` — Android application module
* `libs/` — third-party libraries
* `gradle/`, `gradlew`, `gradlew.bat`, `build.gradle.kts`, `settings.gradle.kts` — Gradle configuration
* `.idea/` — IDE settings (ignored by default in forks)

## Coding style

* Follow standard Java conventions and Android best practices.
* Keep UI strings in `res/values/strings.xml`.
* Use meaningful resource names (snake\_case for resources, camelCase for Java variables and methods).
* Keep methods short and single-responsibility.

Recommended guidelines:

* Use descriptive variable and method names.
* Prefer composition over large monolithic classes.
* Avoid duplicating layout and view logic.

## Branching and commits

Fork the repository and create a feature branch from `main`:

```bash
git checkout -b feature/short-description
```

Commit messages should be short and imperative. Include issue numbers when applicable:

```
Add login screen
Fix crash on rotation
```

Use small, focused commits. Rebase or squash before opening a pull request if necessary.

## Submitting a pull request

1. Push your feature branch to your fork.
2. Open a pull request against `main` in the upstream repository.
3. In the PR description include:

   * A short summary of changes
   * Motivation and context
   * Steps to build and test the change
   * Screenshots or recordings for UI changes
4. Address review comments and keep the PR history clean.

## Reporting issues

When reporting bugs include:

* A clear title and summary
* Steps to reproduce
* Expected behavior vs actual behavior
* Device/emulator model and Android version
* Relevant logs or stack traces

Feature requests should explain the use case and the proposed solution or API.

## Testing and linting

Run unit tests and Android tests with Gradle:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

Run lint checks:

```bash
./gradlew lint
```

Fix warnings and failing checks before submitting a PR.

## Code review process

* Maintainers will review pull requests and may request changes.
* Keep PRs focused and small to speed up reviews.
* Respond to review comments and update the PR promptly.

## License and contact

This repository currently does not list a formal contribution license. By contributing, you agree that your changes will be made available under the repository's chosen license.

For questions or coordination, open an issue or create a pull request. Thank you for contributing!
