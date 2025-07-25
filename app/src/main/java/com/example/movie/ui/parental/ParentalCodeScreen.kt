package com.example.movie.ui.parental

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movie.ui.components.AlertBox
import com.example.movie.ui.components.AlertState
import com.example.movie.ui.components.PinCodeInputState
import com.example.movie.ui.components.PincodeInputComposable
import com.example.movie.ui.theme.MovieTheme

@Composable
fun ParentalCodeScreen(
    pinCodeInputState: PinCodeInputState,
    onPinChanged: (String) -> Unit,
    onPinEntered: (String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var pinInput by remember { mutableStateOf("") }
    val alertVisible = remember { mutableStateOf(false) }
    var alertState: AlertState by remember { mutableStateOf(AlertState.Success) }
    var alertMessage by remember { mutableStateOf("") }

    LaunchedEffect(pinCodeInputState) {
        alertVisible.value = when (pinCodeInputState) {
            is PinCodeInputState.Success -> {
                alertState = AlertState.Success
                alertMessage = "PIN Correct!"
                true
            }
            is PinCodeInputState.Error -> {
                alertState = AlertState.Failure
                alertMessage = "Invalid PIN. Please try again."
                true
            }
            else -> false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        IconButton(
            onClick = onNavigateUp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Parental code",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your 4-digit parental code",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            PincodeInputComposable(
                input = pinInput,
                hidden = false,
                onValueChange = { newValue ->
                    pinInput = newValue
                    onPinChanged(newValue)
                    if (newValue.length == 4) {
                        onPinEntered(newValue)
                    }
                },
                state = pinCodeInputState
            )

            Spacer(modifier = Modifier.height(16.dp))

            AlertBox(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                visible = alertVisible,
                alertState = alertState,
                message = alertMessage,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Forgotten your parental code?",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.clickable { /* Handle forgotten code */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParentalCodeScreenPreview() {
    MovieTheme {
        ParentalCodeScreen(
            pinCodeInputState = PinCodeInputState.Default,
            onPinChanged = {},
            onPinEntered = {},
            onNavigateUp = {}
        )
    }
}