import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.androidMultiplatformLibrary)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
  androidLibrary {
    namespace = "io.github.masaibar.toastcmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    minSdk = libs.versions.android.minSdk.get().toInt()

    withHostTestBuilder {}.configure {}

    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
    }
  }

  listOf(
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ToastCMP"
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

    commonTest.dependencies {
      implementation(kotlin("test"))
    }
  }
}
