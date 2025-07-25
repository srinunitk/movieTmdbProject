package com.example.movie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movie.ui.theme.MovieTheme

private const val PIN_LENGTH = 4

sealed class PinCodeInputState {
    object Default : PinCodeInputState()
    object Error : PinCodeInputState()
    object Success : PinCodeInputState()
}

@Composable
fun PincodeInputComposable(
    input: String,
    hidden: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: PinCodeInputState = PinCodeInputState.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val borderColor = when {
        isFocused -> Color.White
        else -> when (state) {
            is PinCodeInputState.Error -> Color.Red
            is PinCodeInputState.Success -> Color.Green
            else -> Color.Gray
        }
    }

    BasicTextField(
        value = input,
        onValueChange = {
            if (it.length <= PIN_LENGTH) {
                onValueChange(it.filter { char -> char.isDigit() })
            }
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        keyboardOptions = keyboardOptions,
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(PIN_LENGTH) { index ->
                    val hasChar = input.length > index
                    val char = if (hasChar) input[index] else ' '
                    val displayChar = if (hidden && hasChar) '•' else char

                    val cellBorderColor = if (isFocused && index == input.length) {
                        Color.White
                    } else {
                        borderColor
                    }

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF2C2C2C))
                            .border(
                                width = 1.dp,
                                color = cellBorderColor,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = displayChar.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PincodeInputComposablePreviewDefault() {
    MovieTheme {
        var input by remember { mutableStateOf("12") }
        PincodeInputComposable(
            input = input,
            hidden = false,
            onValueChange = { input = it }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PincodeInputComposablePreviewHidden() {
    MovieTheme {
        var input by remember { mutableStateOf("123") }
        PincodeInputComposable(
            input = input,
            hidden = true,
            onValueChange = { input = it }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PincodeInputComposablePreviewError() {
    MovieTheme {
        var input by remember { mutableStateOf("1234") }
        PincodeInputComposable(
            input = input,
            hidden = false,
            onValueChange = { input = it },
            state = PinCodeInputState.Error
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PincodeInputComposablePreviewSuccess() {
    MovieTheme {
        var input by remember { mutableStateOf("1234") }
        PincodeInputComposable(
            input = input,
            hidden = true,
            onValueChange = { input = it },
            state = PinCodeInputState.Success
        )
    }
}