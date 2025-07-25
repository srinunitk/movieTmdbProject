package com.example.movie.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movie.ui.theme.MovieTheme

@Preview(showBackground = true)
@Composable
fun AlertBoxSuccessPreview() {
    val isVisible = remember { mutableStateOf(true) }
    MovieTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            AlertBox(
                visible = isVisible,
                alertState = AlertState.Success,
                message = "This is a success message!",
                callToActionText = "OK",
                onCallToAction = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertBoxFailurePreview() {
    val isVisible = remember { mutableStateOf(true) }
    MovieTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            AlertBox(
                visible = isVisible,
                alertState = AlertState.Failure,
                message = "This is an alert message!",
                callToActionText = "Call to Action",
                onCallToAction = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertBoxFailureNoActionPreview() {
    val isVisible = remember { mutableStateOf(true) }
    MovieTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            AlertBox(
                visible = isVisible,
                alertState = AlertState.Failure,
                message = "This is an alert message!"
            )
        }
    }
}