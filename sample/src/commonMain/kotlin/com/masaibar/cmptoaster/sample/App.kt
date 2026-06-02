package com.masaibar.cmptoaster.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masaibar.cmptoaster.Toast
import com.masaibar.cmptoaster.ToastDuration
import com.masaibar.cmptoaster.rememberToast

private enum class Screen { Home, Second }

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {
  MaterialTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
      val toast = rememberToast()
      var screen by remember { mutableStateOf(Screen.Home) }

      // Route the system back (Android button/gesture, iOS swipe-back) to the
      // previous screen instead of closing the app.
      BackHandler(enabled = screen == Screen.Second) {
        screen = Screen.Home
      }

      // Keep content clear of the status bar / camera notch and the home indicator
      // (Surface still fills behind them).
      Box(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        when (screen) {
          Screen.Home -> HomeScreen(
            toast = toast,
            onGoToSecond = { screen = Screen.Second }
          )

          Screen.Second -> SecondScreen(
            onBack = { screen = Screen.Home }
          )
        }
      }
    }
  }
}

@Composable
private fun HomeScreen(
  toast: Toast,
  onGoToSecond: () -> Unit
) {
  var showDialog by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(24.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "CMPToaster Sample",
      style = MaterialTheme.typography.headlineMedium
    )

    SectionTitle("Basics")
    Button(
      onClick = { toast.show("This is a short toast") },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Short toast")
    }
    Button(
      onClick = { toast.show("This is a long toast", ToastDuration.LONG) },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Long toast")
    }

    SectionTitle("Shows above a dialog")
    Button(
      onClick = { showDialog = true },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Open dialog")
    }

    SectionTitle("Survives navigation")
    Button(
      onClick = {
        toast.show("This toast survives navigation", ToastDuration.LONG)
        onGoToSecond()
      },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Show toast and go to next screen")
    }
  }

  if (showDialog) {
    AlertDialog(
      onDismissRequest = { showDialog = false },
      title = { Text("A Compose dialog") },
      text = { Text("Tap the button below — the toast appears on top of this dialog.") },
      confirmButton = {
        TextButton(onClick = { toast.show("Shown above the dialog") }) {
          Text("Show toast")
        }
      },
      dismissButton = {
        TextButton(onClick = { showDialog = false }) {
          Text("Close")
        }
      }
    )
  }
}

@Composable
private fun SecondScreen(onBack: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Second screen",
      style = MaterialTheme.typography.headlineMedium
    )
    Text(
      text = "The toast from the previous screen should still be visible here.",
      textAlign = TextAlign.Center
    )
    OutlinedButton(onClick = onBack) {
      Text("Back")
    }
  }
}

@Composable
private fun SectionTitle(text: String) {
  Text(
    text = text,
    style = MaterialTheme.typography.titleSmall,
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 8.dp)
  )
}
