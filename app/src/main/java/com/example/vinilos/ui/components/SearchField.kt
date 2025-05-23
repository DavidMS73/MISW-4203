package com.example.vinilos.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun SearchField(
    value: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        leadingIcon = {
            Icon(
                Icons.Filled.Search, contentDescription = null,
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onClear()
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .testTag("ClearButton")
                ) {
                    Icon(
                        Icons.Filled.Cancel,
                        contentDescription = "Limpiar filtro",
                    )
                }
            }
        },
        value = value,
        placeholder = { Text(placeholderText) },
        onValueChange = onChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences,
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() },
        ),
        modifier = modifier
            .focusRequester(focusRequester)
            .testTag(testTag ?: "SearchTextField")
    )
}
