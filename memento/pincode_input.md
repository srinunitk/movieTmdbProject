# PincodeInputComposable

A secure, reusable composable for 4-digit PIN entry in Jetpack Compose. This component visually displays four separate input boxes and handles all necessary input behaviors, including keyboard control, secure entry, and visual state feedback.

The final implementation uses a robust architecture where a transparent `BasicTextField` is layered on top of the visible PIN boxes. This avoids focus-related crashes and provides a stable input experience.

---

## Parameters

| Parameter | Type | Description |
|---|---|---|
| `input` | `String` | Current PIN input value (up to 4 digits). |
| `hidden` | `Boolean` | If `true`, masks the input digits (e.g., with dots `•`). |
| `onValueChange` | `(String) -> Unit` | Callback triggered on each input change. |
| `state` | `PinCodeInputState` | Visual state for the PIN boxes: `Default`, `Error`, or `Success`. |
| `keyboardOptions`| `KeyboardOptions` | Customizes the keyboard type (defaults to `KeyboardType.NumberPassword`). |

---

## Key Features

- **PIN Length**: Displays exactly 4 input boxes in a horizontal row.
- **Visual States**: The border color of the boxes changes based on the `state` parameter.
  - `Default`: No border.
  - `Error`: A **red** border.
  - `Success`: A **green** border.
- **Active Focus Highlighting**: The currently active input box (where the next digit will appear) is highlighted with a **white** border. This is managed internally by tracking the `isFocused` state of the input field.
- **Secure Display**:
  - When `hidden = true`, the actual digits are replaced with dots (`•`).
- **Keyboard Behavior**:
  - The keyboard is shown automatically when the composable gains focus.
  - Only numeric input is allowed.
  - Backspace deletes the last entered digit.
- **Input Handling**:
  - Input is captured through an invisible `BasicTextField` layered over the visual boxes.
  - Input is limited to a maximum of 4 numeric characters.