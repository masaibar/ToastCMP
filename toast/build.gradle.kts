import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.androidLibrary)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.mavenPublish)
}

kotlin {
  androidTarget {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
    }
  }

  listOf(
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "CMPToaster"
      isStatic = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(compose.runtime)
    }

    // compose.ui is only needed for LocalContext on Android.
    androidMain.dependencies {
      implementation(compose.ui)
    }

    getByName("androidUnitTest").dependencies {
      implementation(libs.junit)
      implementation(libs.robolectric)
    }
  }
}

android {
  namespace = "com.masaibar.cmptoaster"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

mavenPublishing {
  coordinates("com.masaibar", "cmp-toaster", "1.0.0")
  pom {
    name.set("CMPToaster")
    description.set("A tiny Android-style toast for Compose Multiplatform (Android + iOS).")
    inceptionYear.set("2026")
    url.set("https://github.com/masaibar/CMPToaster")
    licenses {
      license {
        name.set("MIT License")
        url.set("https://opensource.org/licenses/MIT")
      }
    }
    developers {
      developer {
        id.set("masaibar")
        name.set("masaibar")
        url.set("https://github.com/masaibar")
      }
    }
    scm {
      url.set("https://github.com/masaibar/CMPToaster")
      connection.set("scm:git:git://github.com/masaibar/CMPToaster.git")
      developerConnection.set("scm:git:ssh://git@github.com/masaibar/CMPToaster.git")
    }
  }
}
