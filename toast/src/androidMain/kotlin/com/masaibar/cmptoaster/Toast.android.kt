@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.masaibar.cmptoaster

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast as NativeToast

actual class Toast(
  private val context: Context
) {
  actual fun show(
    message: String,
    duration: ToastDuration
  ) {
    val toastDuration = when (duration) {
      ToastDuration.SHORT -> NativeToast.LENGTH_SHORT
      ToastDuration.LONG -> NativeToast.LENGTH_LONG
    }
    NativeToast.makeText(
      context,
      message,
      toastDuration
    ).show()
  }
}

@Composable
actual fun rememberToast(): Toast {
  val context = LocalContext.current
  return remember { Toast(context) }
}
