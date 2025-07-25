package com.example.movie.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AlertBox(
    modifier: Modifier = Modifier,
    visible: MutableState<Boolean>,
    alertState: AlertState,
    message: String,
    callToActionText: String? = null,
    onCallToAction: () -> Unit = {}
) {
    LaunchedEffect(alertState) {
        if (alertState is AlertState.Success) {
            delay(5000) // Auto-dismiss after 5 seconds for success state
            visible.value = false
        }
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = expandVertically(animationSpec = tween(300)),
        exit = shrinkVertically(animationSpec = tween(300)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(alertState.backgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(alertState.iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = alertState.icon,
                    contentDescription = null,
                    tint = alertState.iconTint,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = alertState.textColor, fontSize = 14.sp)) {
                    append(message)
                }
                if (callToActionText != null) {
                    append(" ")
                    pushStringAnnotation(tag = "CTA", annotation = "call_to_action")
                    withStyle(style = SpanStyle(
                        color = alertState.callToActionColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp)
                    ) {
                        append(callToActionText)
                    }
                    pop()
                }
            }

            ClickableText(
                text = annotatedString,
                modifier = Modifier.weight(1f),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "CTA", start = offset, end = offset)
                        .firstOrNull()?.let {
                            onCallToAction()
                        }
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = alertState.textColor,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { visible.value = false }
            )
        }
    }
}

sealed class AlertState(
    val backgroundColor: Color,
    val icon: ImageVector,
    val iconBackgroundColor: Color,
    val iconTint: Color,
    val textColor: Color,
    val callToActionColor: Color
) {
    object Success : AlertState(
        backgroundColor = Color(0xFFE6F4EA),
        icon = Icons.Default.CheckCircle,
        iconBackgroundColor = Color(0xFF34A853),
        iconTint = Color.White,
        textColor = Color.DarkGray,
        callToActionColor = Color(0xFF34A853)
    )

    object Failure : AlertState(
        backgroundColor = Color(0xFF3E1A1A),
        icon = Icons.Default.Close, // close icon for the red circle
        iconBackgroundColor = Color(0xFFD32F2F),
        iconTint = Color.White,
        textColor = Color.White,
        callToActionColor = Color(0xFFEF9A9A) // A lighter red for call to action
    )
}