package com.example.movie.ui.detail.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.movie.ui.components.PinCodeInputState
import com.example.movie.ui.parental.ParentalCodeScreen

@Composable
fun ParentalCodeValidation(
    onValidated: (Boolean) -> Unit,
    onNavigateUp: () -> Unit
) {
    var pinCodeInputState: PinCodeInputState by remember { mutableStateOf(PinCodeInputState.Default) }

    ParentalCodeScreen(
        pinCodeInputState = pinCodeInputState,
        onPinChanged = { pin ->
            if (pin.length < 4) {
                pinCodeInputState = PinCodeInputState.Default
            }
        },
        onPinEntered = { pin ->
            if (pin == "1234") {
                pinCodeInputState = PinCodeInputState.Success
                onValidated(true)
            } else {
                pinCodeInputState = PinCodeInputState.Error
            }
        },
        onNavigateUp = onNavigateUp
    )
}