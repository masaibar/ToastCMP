package io.github.masaibar.toastcmp

import kotlin.test.Test
import kotlin.test.assertEquals

class ToastDurationTest {

  @Test
  fun exposesShortAndLong() {
    assertEquals(
      expected = listOf(ToastDuration.SHORT, ToastDuration.LONG),
      actual = ToastDuration.entries
    )
  }
}
