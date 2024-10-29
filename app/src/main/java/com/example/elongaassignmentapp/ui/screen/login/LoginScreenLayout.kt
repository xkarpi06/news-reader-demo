package com.example.elongaassignmentapp.ui.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.elongaassignmentapp.R
import com.example.elongaassignmentapp.ui.screen.login.model.LoginUIState
import com.example.elongaassignmentapp.ui.screen.login.model.UserCredentials
import com.example.elongaassignmentapp.ui.theme.AppTheme
import com.example.elongaassignmentapp.ui.theme.Dimens

@Composable
fun LoginScreenLayout(
    uiState: LoginUIState,
    onSubmit: (UserCredentials) -> Unit = {},
    onSkip: () -> Unit = {},
) {
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val hasUserAttemptedToLogin = rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.L),
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.screen_login_welcome_to_newsdata_io),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                // Username
                OutlinedTextField(
                    value = username.value,
                    onValueChange = { username.value = it },
                    modifier = Modifier.padding(top = Dimens.L),
                    shape = CircleShape,
                    label = { Text(stringResource(R.string.screen_login_username)) },
                    isError = hasUserAttemptedToLogin.value && username.value.isEmpty(),
                    trailingIcon = {
                        if (username.value.isNotEmpty()) {
                            IconButton(onClick = { username.value = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Cancel,
                                    contentDescription = stringResource(R.string.screen_login_cd_clear_username)
                                )
                            }
                        }
                    },
                )

                // Password
                var showPassword by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier.padding(top = Dimens.M),
                    shape = CircleShape,
                    label = { Text(stringResource(R.string.screen_login_password)) },
                    visualTransformation = if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    isError = hasUserAttemptedToLogin.value && password.value.isEmpty(),
                    trailingIcon = {
                        if (password.value.isNotEmpty()) {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) {
                                        Icons.Filled.VisibilityOff
                                    } else {
                                        Icons.Filled.Visibility
                                    },
                                    contentDescription = stringResource(R.string.screen_login_cd_show_hide_password)
                                )
                            }
                        }
                    },
                )

                // Error message
                if (uiState.errorMessage.isNotEmpty()) {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = Dimens.S)
                    )
                }

                // Sign In button
                Button(
                    onClick = {
                        hasUserAttemptedToLogin.value = true
                        if (username.value.isNotEmpty() && password.value.isNotEmpty()) {
                            onSubmit(UserCredentials(username = username.value, password = password.value))
                        }
                    },
                    modifier = Modifier.padding(top = Dimens.M)
                ) {
                    Text(stringResource(R.string.screen_login_sign_in))
                }

                // Skip button
                TextButton(onClick = onSkip) {
                    Text(stringResource(R.string.screen_login_skip))
                }
            }

            Spacer(modifier = Modifier.weight(3f))
        }
    }
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        LoginScreenLayout(LoginUIState())
    }
}
