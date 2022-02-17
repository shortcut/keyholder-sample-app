# Shortcut android template

This repo serves as a starting point for many applications.  
It is here to solve common problems and implement common features that we have in most applications.


## Features:
* Simple CI with github actions
* Dependency injection
* Splash screen
* Version lock
* Onboarding
* Preferences
* Navigation architecture
* Logging and crash reporting
* Messaging / Notifications
* Analytics
* Versioning
* A starting point for using and manipulating android material design
* Release configuration (Just add a keystore and signing.properties to `keys/release`.)

This template assumes that you will use:
- Kotlin
- Data binding
- Single activity architecture with NavController

Configured with:
- Kotlin DSL gradle
- Firebase messaging
- Firebase analytics.
- Firebase Crashlytics.
- Dependency injection with Koin
- androidx Datastore Preferences
- Timber for logging
- Versioning with https://github.com/tslamic/versioning
- Several commonly used data binding libraries

The services are loosely coupled and it should be easy to swap Firebase out and use a different analytics provider, crash reporting tool etc.

## Setup

After cloning the project:
1. From android studio right click on `app/src/main/java/shortcut/androidtemplate` and refactor the package name to your own package name.
2. Replace the package name in `fastlane/Appfile`.
3. Create a firebase project and replace `app/google-services.json` with your own.
4. Several layouts have used `@drawable/ic_launcher_background` as a placeholder image. You probably want to replace those with your own images.
5. AFragment and BFragment exist to give you an example of navigation. Feel free to rename or delete them. You also have to update `nav_graph.xml` to reflect the changes you did.
6. `shortcut` has been used in many places, for example in naming of styles. You might want to rename the styles, as you will have to change them quite a lot to get your own design anyway. They are only there to give you an idea of how to implement your own styles.

CI tests and builds the code, then uses spotless&ktlint to check formatting etc. You can run `./gradlew spotlessApply` to automatically format the code.

It is recommended to add a precommit hook that automatically formats the code and aborts a commit if it is not correctly formatted.
You can add this to `.git/hooks/pre-commit`:
```
#!/bin/sh
set -e
STATUS="$(git status)"
./gradlew -PdisableSpotlessCheck spotlessApply
if [[ $STATUS != "$(git status)" ]]; then
  echo "$(tput setaf 1)Failed to commit, spotlessApply had changes$(tput sgr0)"
  exit 1
fi

```

## CI/CD

CI is setup with Github Actions and Fastlane.
CI runs spotlessCheck, testDebugUnitTest and bundleRelease.
If you add flavors you have to update the gradle tasks in .github/workflows/ci.yml, e.g `bundleProdRelease` instead of `bundleRelease`.

### Enable automatic deploy to Google Play internal:
1. Go to https://console.cloud.google.com/apis/library
2. Make sure the correct project is selected in cloud console. You should have a project already if you setup firebase.
3. Search for Google Play Android Developer API and enable it.
4. Go to IAM & Admin -> Service Accounts
5. Create a service account and give it the "Service Account User" role
6. Click on the three dots on the new service account -> Manage keys -> ADD KEY -> Create new Key -> JSON -> CREATE -> save the file
7. Go to your github repo -> Settings -> Secrets -> New repository secret
8. Enter "GOOGLE_PLAY_SERVICE_KEY" as name, and the service account JSON you downloaded as value
9. Go to https://play.google.com/console
10. Setup -> API Access
11. Select your google cloud project.
12. Find the service account you created -> View access -> Add app
13. Add your app and give the service account admin access
14. Uncomment .github/workflows/tag.yml
15. Push a tag and verify that a new version is deployed to the internal track on play console!

## Tests

`android-template` is *not* a library. It is intended to be cloned and changed. If your code is not intended to be changed and you wrote tests for it, it probably belongs to a library, not the template. There is a strong requirement that new code added to the template should already be in production in another app, which means it has already been tested extensively. Even bugs in the template should not be fixed unless that bug-fix has been done in a production app first.

## Contributing

Anyone in Shortcut can contribute to this template.

Is your PR a generic binding adapter that could be useful to all projects? Please add it to our [data-binding library](https://github.com/shortcut/data-binding).

A PR should include a short description with the following:

1. Why should this be added to the template?
2. Where has the solution been used previously? (It should already be in production in at least one other app)
