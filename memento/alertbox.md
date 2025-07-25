# AlertBox Composable

## Overview

The `AlertBox` is a reusable Jetpack Compose component designed to display alert messages with support for different states, such as success and failure. It is customizable and includes an optional call-to-action (CTA) button.

## Features

- **State-driven:** The component's appearance changes based on the `AlertState` (e.g., `Success`, `Failure`).
- **Customizable:** The message and CTA text can be configured.
- **Dismissible:** The alert can be dismissed by clicking a close icon.
- **Auto-dismiss:** Success alerts automatically dismiss after a short delay.
- **Animated:** The component uses animations for appearing and disappearing.

## Usage

To use the `AlertBox`, you need to provide a `MutableState` for visibility, an `AlertState`, a message, and an optional CTA with its action.

### Example

```kotlin
val isVisible = remember { mutableStateOf(true) }

AlertBox(
    visible = isVisible,
    alertState = AlertState.Failure,
    message = "This is an alert message!",
    callToActionText = "Call to Action",
    onCallToAction = { /* Handle CTA click */ }
)
```

## States

The `AlertState` is a sealed class that defines the visual properties for each state:

- **`AlertState.Success`**: Used for success messages. It has a green theme and a checkmark icon.
- **`AlertState.Failure`**: Used for error or failure messages. It has a red theme and a close icon, as shown in the reference image.

Each state defines its own:
- `backgroundColor`
- `icon`
- `iconBackgroundColor`
- `iconTint`
- `textColor`
- `callToActionColor`