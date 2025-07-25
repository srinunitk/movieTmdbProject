# Parental Code Feature

## Overview

The Parental Code feature provides a security gate for content within the application, requiring users to enter a 4-digit PIN to proceed. It is composed of a dedicated UI screen (`ParentalCodeScreen`) and a validation component (`ParentalCodeValidation`) that manages the logic.

---

## Components

### 1. `ParentalCodeScreen.kt`

This file defines the user interface for the parental code entry screen.

- **Purpose**: To present a visually clean and intuitive interface for PIN entry.
- **Location**: `app/src/main/java/com/example/movie/ui/parental/ParentalCodeScreen.kt`

#### Key UI Elements:
- **Title and Subtitle**: Informs the user what action is required.
- **`PincodeInputComposable`**: The core component for entering the 4-digit PIN. Its visual state (`Default`, `Error`, `Success`) is controlled externally.
- **`AlertBox`**: Provides contextual feedback (success or error messages) to the user directly below the PIN input field.
- **"Forgotten Code" Link**: A clickable text element for future functionality.
- **Back Navigation**: An arrow icon to allow the user to navigate back.

### 2. `ParentalCodeValidation.kt`

This composable wraps the `ParentalCodeScreen` and contains the business logic for PIN validation.

- **Purpose**: To manage the state of the PIN input and validate the entered code.
- **Location**: `app/src/main/java/com/example/movie/ui/detail/components/ParentalCodeValidation.kt`

---

## Validation Logic

The validation logic is handled within the `onPinEntered` and `onPinChanged` lambdas in `ParentalCodeValidation`.

- **Success Condition**: If the entered PIN is exactly `"1234"`.
  - The `pinCodeInputState` is set to `PinCodeInputState.Success`.
  - The `onValidated(true)` callback is invoked to notify the parent composable (e.g., `MovieDetailScreen`) that the validation was successful.

- **Error Condition**: If the 4-digit PIN is anything other than `"1234"`.
  - The `pinCodeInputState` is set to `PinCodeInputState.Error`.

- **Default State**:
  - The state is `PinCodeInputState.Default` initially.
  - It is reset to `Default` in `onPinChanged` whenever the PIN length is less than 4, clearing any previous `Error` or `Success` states as the user types.