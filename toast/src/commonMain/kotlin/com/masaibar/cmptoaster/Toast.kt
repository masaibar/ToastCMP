@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.masaibar.cmptoaster

import androidx.compose.runtime.Composable

/**
 * A lightweight, Android-style toast for Compose Multiplatform.
 *
 * - **Android**: delegates to the native [android.widget.Toast].
 * - **iOS**: there is no native toast, so a transient overlay is rendered on a
 *   dedicated [platform.UIKit.UIWindow] above the key window. It survives screen
 *   transitions and never blocks user interaction.
 */
expect class Toast {
  /**
   * Shows [message] for the given [duration].
   *
   * Safe to call from any thread; the toast is always presented on the main thread.
   */
  fun show(
    message: String,
    duration: ToastDuration = ToastDuration.SHORT
  )
}

enum class ToastDuration {
  SHORT,
  LONG
}

/**
 * Returns a [Toast] bound to the current platform context.
 *
 * On Android it captures the `LocalContext`, so call it from within composition.
 */
@Composable
expect fun rememberToast(): Toast
