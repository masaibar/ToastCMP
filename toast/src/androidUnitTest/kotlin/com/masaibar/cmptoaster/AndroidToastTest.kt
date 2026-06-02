package com.masaibar.cmptoaster

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast
import android.widget.Toast as AndroidToast

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AndroidToastTest {

  @Test
  fun show_displaysToastWithGivenText() {
    val toast = Toast(RuntimeEnvironment.getApplication())

    toast.show("Saved!", ToastDuration.SHORT)

    assertEquals("Saved!", ShadowToast.getTextOfLatestToast())
  }

  @Test
  fun show_mapsLongDurationToNativeLengthLong() {
    val toast = Toast(RuntimeEnvironment.getApplication())

    toast.show("Uploaded", ToastDuration.LONG)

    assertEquals(AndroidToast.LENGTH_LONG, ShadowToast.getLatestToast().duration)
  }
}
