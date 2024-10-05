package com.example.elongaassignmentapp.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.elongaassignmentapp.ui.theme.AppTheme

@Composable
internal fun LoginScreenLayout(
    onLoginClick: () -> Unit = {},
) {
    Column {
        Text(text = "Login Screen")
        Button(onClick = onLoginClick) {
            Text("Sign In")
        }
    }
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        LoginScreenLayout()
    }
}
