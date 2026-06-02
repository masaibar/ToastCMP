@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.github.masaibar.toastcmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSOperationQueue
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowLevelAlert
import platform.UIKit.UIWindowScene
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

actual class Toast {
  private var toastWindow: UIWindow? = null
  private var toastView: UIView? = null

  actual fun show(
    message: String,
    duration: ToastDuration
  ) {
    // UIKit must be touched on the main thread.
    NSOperationQueue.mainQueue.addOperationWithBlock {
      hideCurrentToast()
      showNewToast(message, duration)
    }
  }

  private fun hideCurrentToast() {
    toastView?.removeFromSuperview()
    toastWindow?.setHidden(true)
    toastWindow = null
    toastView = null
  }

  private fun showNewToast(
    message: String,
    duration: ToastDuration
  ) {
    // A dedicated window keeps the toast visible across screen transitions.
    val window = UIWindow().apply {
      windowLevel = UIWindowLevelAlert + 1.0
      backgroundColor = UIColor.clearColor
      setHidden(false)
      setUserInteractionEnabled(false)
    }

    // Attach to the active scene (iOS 13+).
    UIApplication.sharedApplication.connectedScenes
      .filterIsInstance<UIWindowScene>()
      .firstOrNull()
      ?.let { scene ->
        window.windowScene = scene
      }

    val containerView = UIView().apply {
      backgroundColor = UIColor.blackColor.colorWithAlphaComponent(0.8)
      layer.cornerRadius = 10.0
      translatesAutoresizingMaskIntoConstraints = false
    }

    val label = UILabel().apply {
      text = message
      textColor = UIColor.whiteColor
      textAlignment = NSTextAlignmentCenter
      numberOfLines = 0
      font = UIFont.systemFontOfSize(14.0)
      translatesAutoresizingMaskIntoConstraints = false
    }

    containerView.addSubview(label)
    window.addSubview(containerView)

    NSLayoutConstraint.activateConstraints(
      listOf(
        label.leadingAnchor.constraintEqualToAnchor(containerView.leadingAnchor, 16.0),
        label.trailingAnchor.constraintEqualToAnchor(containerView.trailingAnchor, -16.0),
        label.topAnchor.constraintEqualToAnchor(containerView.topAnchor, 12.0),
        label.bottomAnchor.constraintEqualToAnchor(containerView.bottomAnchor, -12.0),
        containerView.centerXAnchor.constraintEqualToAnchor(window.centerXAnchor),
        containerView.bottomAnchor.constraintEqualToAnchor(window.safeAreaLayoutGuide.bottomAnchor, -100.0),
        containerView.leadingAnchor.constraintGreaterThanOrEqualToAnchor(window.leadingAnchor, 20.0),
        containerView.trailingAnchor.constraintLessThanOrEqualToAnchor(window.trailingAnchor, -20.0),
        containerView.widthAnchor.constraintLessThanOrEqualToConstant(300.0)
      )
    )

    toastWindow = window
    toastView = containerView

    containerView.alpha = 0.0
    UIView.animateWithDuration(0.3) {
      containerView.alpha = 1.0
    }

    val dismissDelay = when (duration) {
      ToastDuration.SHORT -> (2.0 * NSEC_PER_SEC.toDouble()).toLong()
      ToastDuration.LONG -> (3.5 * NSEC_PER_SEC.toDouble()).toLong()
    }

    dispatch_after(
      dispatch_time(DISPATCH_TIME_NOW, dismissDelay),
      dispatch_get_main_queue()!!
    ) {
      UIView.animateWithDuration(
        duration = 0.3,
        animations = {
          containerView.alpha = 0.0
        },
        completion = { _ ->
          hideCurrentToast()
        }
      )
    }
  }
}

@Composable
actual fun rememberToast(): Toast = remember { Toast() }
