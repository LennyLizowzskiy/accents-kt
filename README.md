[//]: # (<img src="https://github.com/LennyLizowzskiy/accents-kt/assets/46971551/b55dca62-cafa-45dc-a794-09675755948b" width="64" align="right" alt="logo"/>)
[![](https://jitpack.io/v/LennyLizowzskiy/accents-kt.svg)](https://jitpack.io/#LennyLizowzskiy/accents-kt)

# accents.kt
Kotlin Multiplatform library for retrieving accent color in a simple way.

## Supported platforms
- [ ] JVM / Android
- [x] JVM / Windows
- [x] JVM / macOS
- [x] JVM / Linux _(GTK)_
- [x] JVM / Linux _(KDE)_
- [x] JVM / Linux _(FreeDesktop, dbus)_
- [ ] Native / macOS
- [ ] Native / Linux
- [ ] Native / Windows

> Web targets won't be supported. Check [developer.mozilla/css/accent-color](https://developer.mozilla.org/en-US/docs/Web/CSS/accent-color) if you need this feature.

Also, I don't guarantee that I will be able to support Native and/or macOS targets, so PRs are always welcome.

### Requirements
|            Platform             | /stuff/                                                                                                                                                             |
|:-------------------------------:|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|          JVM / Windows          | version 10 or higher, `reg query` access for the caller                                                                                                             |
|           JVM / macOS           | version 11 or higher, `sw_vers` access for the caller                                                                                                               |
|       JVM / Linux (GTK3)        | GTK3 config dir (`_CONFIG_DIR_/gtk-3.0`) access for the caller                                                                                                      |
|        JVM / Linux (KDE)        | KDE config dir (`_CONFIG_DIR_/plasma-org.kde.plasma.desktop-appletsrc`) access for the caller, tested on KDE 5                                                      |
| JVM / Linux (FreeDesktop, dbus) | `dbus-send --user` access for the caller, XDG Desktop Portal must be installed in the system and it must support `org.freedesktop.appearance.color-scheme` protocol |

## Usage
### Configure dependencies
1. Add `jitpack.io` repository in your root build.gradle
```groovy
// build.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
        // ...
    }
}
```
2. Add the dependency
```groovy
dependencies {
    // replace RELEASE_TAG with the latest release from the Releases or Tags page
    implementation 'com.github.LennyLizowzskiy:accents-kt:RELEASE_TAG' 
}
```
## How to use
*A single line of code to get the accent color on any of the supported platforms*
```kotlin
import com.lizowzskiy.accents.getAccentColor

val accent = getAccentColor()
```

Calling from Java:
```java
import com.lizowzskiy.accents.AccentUtil;
import com.lizowzskiy.accents.Color;

class Foo {
    public void bar() {
        Color accent = AccentUtil.getAccentColor();
        System.out.println(accent);
    }
}
```
